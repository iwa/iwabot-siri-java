package sh.iwa;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;


@SpringBootApplication
@RestController
public class ApiController {

    @PostMapping("/ban")
    public Response banUser(@RequestBody BanKickRequest request, HttpServletRequest httpRequest) {
        if (!isAuthorized(httpRequest)) {
            return new Response(403, "Access denied");
        }

        try {
            Guild guild = Client.getGuild(Client.GUILD_ID);
            if (guild == null) {
                return new Response(404, "Guild not found");
            }

            Member member = Client.getMember(guild, request.getUserId());
            if (member == null) {
                return new Response(404, "Member not found");
            }

            Client.banMember(member, "Banned by API");
            return new Response(200, "Member banned successfully");
        } catch (ErrorResponseException e) {
            return new Response(500, "Error banning member: " + e.getMessage());
        } catch (Exception e) {
            return new Response(500, "An unexpected error occurred");
        }
    }

    @PostMapping("/kick")
    public Response kickUser(@RequestBody BanKickRequest request, HttpServletRequest httpRequest) {
        if (!isAuthorized(httpRequest)) {
            return new Response(403, "Access denied");
        }

        try {
            Guild guild = Client.getGuild(Client.GUILD_ID);
            if (guild == null) {
                return new Response(404, "Guild not found");
            }

            Member member = Client.getMember(guild, request.getUserId());
            if (member == null) {
                return new Response(404, "Member not found");
            }

            Client.kickMember(member, "Kicked by API");
            return new Response(200, "Member kicked successfully");
        } catch (ErrorResponseException e) {
            return new Response(500, "Error kicking member: " + e.getMessage());
        } catch (Exception e) {
            return new Response(500, "An unexpected error occurred");
        }
    }

    private boolean isAuthorized(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization");
        return EnvConfig.get("SECRET").equals(authToken);
    }
}
