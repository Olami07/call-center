package com.automation.callcenter.team.service;

import com.automation.callcenter.team.entity.Teams;
import com.automation.callcenter.team.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Teams> getAllTeams() {
        return teamRepository.findAll();
    }

    public Teams getTeamById(Long id) {
        return teamRepository.findById(id).orElse(null);
    }

    public Teams createTeam(Teams team) {
        return teamRepository.save(team);
    }

    public Teams updateTeam(Long id, Teams updatedTeam) {
        Teams team = teamRepository.findById(id).orElse(null);
        if (team != null) {
            team.setTeamCode(updatedTeam.getTeamCode());
            team.setTeamName(updatedTeam.getTeamName());
            team.setUnit(updatedTeam.getUnit());
            team.setManager(updatedTeam.getManager());
            team.setStatus(updatedTeam.getStatus());
            team.setCreatedBy(updatedTeam.getCreatedBy());
            team.setModifiedBy(updatedTeam.getModifiedBy());
            team.setCreated(updatedTeam.getCreated());
            team.setModified(updatedTeam.getModified());
            return teamRepository.save(team);
        }
        return null;
    }

    public boolean deleteTeam(Long id) {
        if (teamRepository.existsById(id)) {
            teamRepository.deleteById(id);
            return true;
        }
        return false;
    }
}