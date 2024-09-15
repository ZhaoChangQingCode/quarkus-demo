import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.io.IOException;
import java.nio.file.Files;

import static org.acme.consts.QuarkusApplicationConsts.*;

@Path("/download")
public class DownloadResource {

    @GET
    @Path("{res}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public String download(@PathParam("res") String res) throws IOException {
        return Files.readString(ROOT_PATH.resolve(res));
    }
}
