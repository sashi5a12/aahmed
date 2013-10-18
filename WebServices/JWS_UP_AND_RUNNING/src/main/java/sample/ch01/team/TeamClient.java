/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.ch01.team;

import java.util.List;
import sample.ch01.team.client.TeamsService;

/**
 *
 * @author aahmed
 */
public class TeamClient {

    public static void main(String[] args) {
        TeamsService service = new TeamsService();
        sample.ch01.team.client.Teams port = service.getTeamsPort();
        List<sample.ch01.team.client.Team> teams = port.getTeams();
        for (sample.ch01.team.client.Team team : teams) {
            System.out.println("Team name: " + team.getName()
                    + " (roster count: " + team.getRosterCount() + ")");
            for (sample.ch01.team.client.Player player : team.getPlayers()) {
                System.out.println(" Player: " + player.getNickname());
            }
        }
    }
}
