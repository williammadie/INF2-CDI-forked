package dev.miage.inf2.course.cdi.endpoint;

import dev.miage.inf2.course.cdi.domain.CandyShop;
import dev.miage.inf2.course.cdi.model.Candy;
import dev.miage.inf2.course.cdi.model.Customer;
import info.schnatterer.mobynamesgenerator.MobyNamesGenerator;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Dependent
@Path("candy")
public class CandiesEndpoint {

    @Inject
    CandyShop candyShop;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance candyList(Collection<Candy> candies);

        public static native TemplateInstance formNew();
    }

    @Path("all")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getCandies() { return Templates.candyList(candyShop.getAllItems());}

    @Path("{id}")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getCandy(@PathParam("id") String id) {
        Optional<Candy> candy = candyShop.getAllItems().stream().filter(b -> b.id().equals(id)).findAny();
        if (candy.isEmpty()) {
            throw new WebApplicationException(404);
        } else {
            return CandiesEndpoint.Templates.candyList(List.of(candy.get()));
        }
    }

    @Path("{id}")
    @DELETE
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance buybook(@PathParam("id") String id) {
        candyShop.sell(new Customer(MobyNamesGenerator.getRandomName(), MobyNamesGenerator.getRandomName(), "toto@miage.dev", "+3395387845"),id);
        return CandiesEndpoint.Templates.candyList(candyShop.getAllItems());
    }

    @Path("form-new")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance newCandyForm() {
        return CandiesEndpoint.Templates.formNew();
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response newCandy(@FormParam("flavor") String flavor, @FormParam("weight") int weight, @FormParam("id") String id) throws URISyntaxException {
        Candy candy = new Candy(flavor, weight, id);
        candyShop.stock(candy);
        return Response.seeOther(new URI("/candy/all")).build();
    }
}
