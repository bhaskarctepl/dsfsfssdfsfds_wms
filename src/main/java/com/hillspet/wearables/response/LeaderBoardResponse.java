package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.LeaderBoard;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LeaderBoardResponse {

	private LeaderBoard currentPet;
	private List<LeaderBoard> leaderBoards;

	public LeaderBoard getCurrentPet() {
		return currentPet;
	}

	public void setCurrentPet(LeaderBoard currentPet) {
		this.currentPet = currentPet;
	}

	public List<LeaderBoard> getLeaderBoards() {
		return leaderBoards;
	}

	public void setLeaderBoards(List<LeaderBoard> leaderBoards) {
		this.leaderBoards = leaderBoards;
	}

}
