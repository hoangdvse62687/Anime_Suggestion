/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.restful;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import sample.dao.movieDao;
import sample.dao.movieMappingCateDao;
import sample.dto.movieDto;

/**
 * REST Web Service
 *
 * @author DELL
 */
@Path("movie")
public class MovieResource {
    private final int defaultPageSize = 20;
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MovieResource
     */
    public MovieResource() {
    }

    /**
     * Retrieves representation of an instance of sample.restful.MovieResource
     * @return an instance of java.lang.String
     */
    @Path("/getTopMovies")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Collection<movieDto> getTopMovies(
    @QueryParam("page") int page,
    @QueryParam("pageSize") int pageSize
    ) {
        if(pageSize == 0){
            pageSize = defaultPageSize;
        }
        movieDao dao = new movieDao();
        List<movieDto> dto = dao.getTopMovie(page, pageSize);
        return dto;
    }
    
    @Path("/getMovieById")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public movieDto getMovieById(
    @QueryParam("id") String id
    ) {
        if(id == null && !id.isEmpty()){
            return null;
        }
        movieDto dto = new movieDto();
        movieDao dao = new movieDao();
        dao.findById(UUID.fromString(id), dto);
        movieMappingCateDao mappingDao = new movieMappingCateDao();
        dto.setMappings(mappingDao.getCateNameByMovieId(id));
        return dto;
    }
    
    @Path("/searchMoviesByName")
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Collection<movieDto> searchMoviesByName(
    @QueryParam("name") String name,
    @QueryParam("page") int page,
    @QueryParam("pageSize") int pageSize
    ) {
        if(pageSize == 0){
            pageSize = defaultPageSize;
        }
        if(name.isEmpty()){
            return null;
        }
        movieDao dao = new movieDao();
        List<movieDto> dto = dao.searchMoviesByName(name,page, pageSize);
        return dto;
    }
}
