package nl.amis.hr.restful;

import com.sun.jersey.api.json.JSONWithPadding;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;

import nl.amis.hr.model.entities.Department;
import nl.amis.hr.model.services.HrSessionEJBLocal;


@Path("department")
@Produces( value = { "application/x-javascript",
                     MediaType.APPLICATION_JSON, 
                     MediaType.APPLICATION_XML})
@Stateless
public class DepartmentRestService {
    public DepartmentRestService() {

    }
    
    @EJB( name = "HrSessionEJB")
    HrSessionEJBLocal hrBean;

    @GET
    public JSONWithPadding  getDepartments( @QueryParam("callback") String callback) {

        List<Department> depts = hrBean.getDepartmentsFindAll();
        if (null == callback) {
             return new JSONWithPadding(new GenericEntity<List<Department>>(depts) {});
         } else {
           return new JSONWithPadding(new GenericEntity<List<Department>>(depts) {}, callback);
         }
    }

    @GET
    @Path("/{id}")
    public  JSONWithPadding getDepartmentById( @PathParam("id") Integer departmentId,
                                               @QueryParam("callback") String callback){
       List<Department> dept = hrBean.getDepartmentFindById(departmentId);
        if ( dept != null && dept.size() >0 ) {
           if (null == callback) {
              return new JSONWithPadding(new GenericEntity<Department>(dept.get(0)) {});

            } else {
              return new JSONWithPadding(new GenericEntity<Department>(dept.get(0)) {
                        }, callback);
            }

        }

        return null;
    }


}