/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;
import sample.constaints.CateEnum;
import sample.constaints.CommonEnum;
import sample.dao.movieDao;
import sample.dao.movieMappingCateDao;
import sample.dao.rawDataDao;
import sample.dto.movieDto;
import sample.dto.movieMappingCateDto;
import sample.dto.rawDataDto;
import sample.objectConfig.Category;
import sample.objectConfig.CategoryMovieMapping;
import sample.objectConfig.Config;
import sample.objectConfig.Configs;
import sample.objectConfig.MangaRanking;
import sample.objectConfig.Movie;
import sample.objectConfig.Movies;
import sample.objectConfig.ObjectReturn;
import sample.objectConfig.WebData;
import sample.utils.CommonUtils;
import sample.utils.DomUtils;
import sample.utils.InternetUtils;
import sample.utils.JaxbUtils;
import sample.utils.StaxUtils;

/**
 *
 * @author DELL
 */
@WebServlet(name = "CrawlDataServlet", urlPatterns = {"/CrawlDataServlet"})
@MultipartConfig(
        fileSizeThreshold   = 1024 * 1024 * 1,
        maxFileSize         = 1024 * 1024 * 10,
        maxRequestSize      = 1024 * 1024 * 15,
        location            = "D:/Uploads"
)
public class CrawlDataServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private final String fileSchema = "WEB-INF/source/configs.xsd";
    private final String fileTopMovie = "Content/source/topmovie.xml";
    private final String defaultPage = "prepareCrawler.jsp";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        ObjectReturn dataReturn = new ObjectReturn();
        rawDataDao rawDataDao = new rawDataDao();
        Part filePart = request.getPart("file");
        InputStream is = filePart.getInputStream();
        String realPath = this.getServletContext().getRealPath("/");
        List<WebData> listWebData = new ArrayList<WebData>();
        List<Movie> listTopMovie = new ArrayList<>();
        try {
               Configs Configs = (Configs)JaxbUtils.bindingXMLToObject(Configs.class,is,realPath + fileSchema);
               List<Config> configs = Configs.getConfig();
               for(Config config : configs){
                    System.out.println("Start Crawl Data: " + config.getUri());
                    //Read file html main of uri
                    WebData webData = DomUtils.getCategories(config);
                    //crawl detail top
                    List<String> hrefs = new ArrayList<String>();
                    for (Movie movie : webData.getListMovies()) {
                        StaxUtils.getMovieDescription(movie.getHrefMovie(),config,movie);
                        hrefs.add(movie.getHrefMovie());
                        //insert to db
                        rawDataDto rawDataDto = new rawDataDto();
                        rawDataDto.setId(movie.getId());
                        rawDataDto.setName(movie.getName());
                        rawDataDto.setDescription(movie.getDetail().getDescription());
                        rawDataDto.setImg(movie.getImg());
                        rawDataDto.setMountOfComment(movie.getMountOfComment());
                        rawDataDto.setMountOfViewed(movie.getMountOfViewed());
                        rawDataDto.setIsTopMovie(movie.isIsTopMovie());
                        rawDataDto.setRateManga(movie.getRateManga());
                        rawDataDto.setEpisode(movie.getDetail().getEpisode());
                        rawDataDto.setYear(movie.getDetail().getYear());

                        rawDataDao.create(rawDataDto);
                    }
                    if(!webData.getCategory().isEmpty()){
                        //save WebData
                        webData.setUri(config.getUri());
                        listWebData.add(webData);
                        
                        //read uri link of webData
                        for (Category category : webData.getCategory()) {
                            List<String> listUriPaging = new ArrayList<String>();
                            listUriPaging.add(category.getHref());//default first paging
                            //adding uri for each main uri categories
                            InternetUtils.getListPagingUri(listUriPaging, config,1,1,config.getMaximumPageGetInCategory());
                            
                            List<Movie> listMovieTmp = new ArrayList<Movie>();
                            //read data from each paging uri
                            for (String uriPagingString : listUriPaging) {
                                try{
                                    StaxUtils.getMovieDetail(uriPagingString, config,listMovieTmp,hrefs);
                                }catch(java.io.IOException ex){
                                    Logger.getLogger(CrawlDataServlet.class.getName()).log(Level.SEVERE,null,ex);
                                }
                            }
                            
                            //read description of movie for each movie in category
                            for (Movie movie : listMovieTmp) {
                                try{
                                    StaxUtils.getMovieDescription(movie.getHrefMovie(),config,movie);
                                    //insert to db
                                    rawDataDto rawDataDto = new rawDataDto();
                                    rawDataDto.setId(movie.getId());
                                    rawDataDto.setName(movie.getName());
                                    rawDataDto.setDescription(movie.getDetail().getDescription());
                                    rawDataDto.setImg(movie.getImg());
                                    rawDataDto.setMountOfComment(movie.getMountOfComment());
                                    rawDataDto.setMountOfViewed(movie.getMountOfViewed());
                                    rawDataDto.setIsTopMovie(movie.isIsTopMovie());
                                    rawDataDto.setRateManga(movie.getRateManga());
                                    rawDataDto.setEpisode(movie.getDetail().getEpisode());
                                    rawDataDto.setYear(movie.getDetail().getYear());

                                    rawDataDao.create(rawDataDto);
                                }catch(java.io.IOException ex){
                                    Logger.getLogger(CrawlDataServlet.class.getName()).log(Level.SEVERE,null,ex);
                                }
                            }
                            
                            webData.getListMovies().addAll(listMovieTmp);
                        }
                    }
               }
               //GET manga rating
               List<String> listUriPaging = new ArrayList<String>();
               listUriPaging.add(CommonEnum.cateRatingUri);
               Config configOfMangaRate = new Config();
               configOfMangaRate.setUri(CommonEnum.cateRatingUri);
               configOfMangaRate.setFormatPagging(CommonEnum.formatPaging);
               configOfMangaRate.setEndformatPagging(CommonEnum.endFormatPaging);
               InternetUtils.getListPagingUri(listUriPaging, configOfMangaRate,CommonEnum.pageSize,0,CommonEnum.maximumPage);
               List<MangaRanking> rankMangas = new ArrayList<>();
               //read data from each paging uri
               for (String uriPagingString : listUriPaging) {
                   try{
                        rankMangas.addAll(StaxUtils.getMangaRanking(uriPagingString));
                   }catch(IOException ex){
                       Logger.getLogger(CrawlDataServlet.class.getName()).log(Level.SEVERE,null,ex);
                   }
                }
               System.out.println("Count Manga Rank Got: " + rankMangas.size());
               
               //merge data
               List<CategoryMovieMapping> mappings = new ArrayList<>();
               List<String> nameMovieMerged = new ArrayList<>();
               List<Movie> data = new ArrayList<>();
               for(int i = 0; i < listWebData.size(); i ++){
                   if( i + 1 < listWebData.size()){
                       if(i == 0){
                          List<Movie> tmp = CommonUtils.mergeMovies(listWebData.get(i).getListMovies(),
                               listWebData.get(i+1).getListMovies(), mappings, nameMovieMerged,realPath,listTopMovie);
                          data.addAll(tmp); 
                       }else{
                           List<Movie> tmp = CommonUtils.mergeMovies(data,
                               listWebData.get(i+1).getListMovies(), mappings, nameMovieMerged,realPath,listTopMovie);
                          data.addAll(tmp); 
                       }
                   }
               }
               movieDao movieDao = new movieDao();
               for(Movie movie : data){
                   for(MangaRanking rank: rankMangas){
                       if(CommonUtils.compareNamePercent(movie.getName(), rank.getName())){
                           movie.setRateManga(rank.getScore());
                       }
                   }
                   
                   //Insert to db
                       movie.calculateRateInSite();
                       movieDto movieDto = new movieDto();
                       movieDto.setId(movie.getId());
                       movieDto.setName(movie.getName());
                       movieDto.setDescription(movie.getDetail().getDescription());
                       movieDto.setImg(movie.getImg());
                       movieDto.setMountOfComment(movie.getMountOfComment());
                       movieDto.setMountOfViewed(movie.getMountOfViewed());
                       movieDto.setIsTopMovie(movie.isIsTopMovie());
                       movieDto.setRateManga(movie.getRateManga());
                       movieDto.setEpisode(movie.getDetail().getEpisode());
                       movieDto.setYear(movie.getDetail().getYear());
                       movieDto.setRateInSite(movie.getRateInSite());
                       movieDao.create(movieDto);
               }
               
               //insert mapping
               movieMappingCateDao cateDao = new movieMappingCateDao();
               for(CategoryMovieMapping mapping : mappings){
                   movieMappingCateDto dto = new movieMappingCateDto();
                   dto.setMovieId(mapping.getMovieId());
                   dto.setNameCate(mapping.getCateNameId());
                   cateDao.create(dto);
               }
               //marshall top list movie
               Movies topMovie = new Movies();
               topMovie.setMovie(listTopMovie);
               JaxbUtils.marshall(Movies.class, topMovie, realPath + fileTopMovie);
               dataReturn.setMessage("Movie Crawl and Merge successful with size of : " + data.size());
               System.out.println("Fin");
           }catch (JAXBException ex) {
                dataReturn.setMessage(ex.toString());
                Logger.getLogger(CrawlDataServlet.class.getName()).log(Level.SEVERE,null,ex);
           }catch(NullPointerException ex){
               dataReturn.setMessage(ex.toString());
               Logger.getLogger(CrawlDataServlet.class.getName()).log(Level.SEVERE,null,ex);
           }catch(IOException ex){
               dataReturn.setMessage(ex.toString());
               Logger.getLogger(CrawlDataServlet.class.getName()).log(Level.SEVERE,null,ex);
           }catch(XMLStreamException ex){
               dataReturn.setMessage(ex.toString());
               Logger.getLogger(CrawlDataServlet.class.getName()).log(Level.SEVERE,null,ex);
           }catch(ParserConfigurationException ex){
               dataReturn.setMessage(ex.toString());
               Logger.getLogger(CrawlDataServlet.class.getName()).log(Level.SEVERE,null,ex);
           }catch(SAXException ex){
               dataReturn.setMessage(ex.toString());
               Logger.getLogger(CrawlDataServlet.class.getName()).log(Level.SEVERE,null,ex);
           }catch(XPathExpressionException ex){
               dataReturn.setMessage(ex.toString());
               Logger.getLogger(CrawlDataServlet.class.getName()).log(Level.SEVERE,null,ex);
           }finally{
                request.setAttribute("data", dataReturn);
                RequestDispatcher rd = request.getRequestDispatcher(defaultPage);
                rd.forward(request, response);
           }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
