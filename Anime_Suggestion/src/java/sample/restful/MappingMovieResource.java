/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.restful;

import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import sample.dao.movieDao;
import sample.dao.movieMappingCateDao;
import sample.dto.movieDto;
import sample.dto.movieMappingCateDto;

/**
 * REST Web Service
 *
 * @author DELL
 */
@Path("mappingMovie")
public class MappingMovieResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MappingMovieResource
     */
    public MappingMovieResource() {
    }

    /**
     * Retrieves representation of an instance of sample.restful.MappingMovieResource
     * @return an instance of java.lang.String
     */
    @Path("/getCategoryByMovieId")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<movieMappingCateDto> getCategoryByMovieId(
    @QueryParam("id") String id
    ) {
        if(id.isEmpty()){
            return null;
        }else{
            movieMappingCateDao dao = new movieMappingCateDao();
            List<movieMappingCateDto> dto = dao.getCateNameByMovieId(id);
            return dto;
        }
    }
    
    @Path("/getMoviesByCateName")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<movieDto> getMoviesByCateName(
    @QueryParam("CateName") String CateName,
    @QueryParam("page") int page,
    @QueryParam("pageSize") int pageSize){
        if(pageSize == 0){
            pageSize = 20;
        }
        if(CateName == null && CateName.isEmpty()){
            return null;
        }
        movieMappingCateDao dao = new movieMappingCateDao();
        List<movieDto> results = dao.getMoviesByCateName(CateName, page, pageSize);
        return results;
    }
    
    @Path("/getMovieSuggestion")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public movieDto getMovieSuggestion(
    @QueryParam("cateName1") String cateName1,
    @QueryParam("cateName2") String cateName2){
        movieMappingCateDao mappingDao = new movieMappingCateDao();
        movieDao movieDao = new movieDao();
        String id = mappingDao.getMovieSuggestionId(cateName1, cateName2);
        MovieResource resource = new MovieResource();
        movieDto dto = resource.getMovieById(id);
        return dto;
    }
}
