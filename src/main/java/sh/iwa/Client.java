package sh.iwa;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;

import java.util.concurrent.TimeUnit;

public class Client {
    private static JDA jda;
    public static final String GUILD_ID = EnvConfig.get("GUILD_ID");

    public static void start(String token) throws Exception {
        jda = JDABuilder.createDefault(token)
                .build();
    }

    public static Guild getGuild(String guildId) throws Exception {
        return jda.awaitReady().getGuildById(guildId);
    }

    public static Member getMember(Guild guild, String userId) throws Exception {
        return guild.retrieveMemberById(userId).complete();
    }

    public static void banMember(Member member, String reason) {
        member.ban(0, TimeUnit.SECONDS).reason(reason).queue();
    }

    public static void kickMember(Member member, String reason) {
        member.kick().reason(reason).queue();
    }
}
