package main.java.model.agents;

import main.java.model.Heuristic;
import main.java.model.SearchAgent;
import main.java.model.game.GameState;
import main.java.model.world.Country;

import java.util.*;

public class AStarAgent extends SearchAgent {
    public List<GameState> pathStates;
    public AStarAgent(Heuristic heuristic, GameState initState) {
        super(heuristic);
        this.pathStates = aStarSearch(initState, heuristic);
    }

    private List<GameState> aStarSearch(GameState initState, Heuristic heuristic) {
        // NOTE: CHECK LAST STATE IN THE LIST TO KNOW IF ASTAR REACHED A SOLUTION OR NOT
        Queue<GameState> frontier = new PriorityQueue<>(Comparator.comparing(heuristic::eval));
        Map<GameState, Integer> heuristicMap = new HashMap<>();
        Map<GameState, GameState> parentsMap = new HashMap<>();

        PassiveAgent passiveAgent = new PassiveAgent();

        frontier.add(initState);
        heuristicMap.put(initState, heuristic.eval(initState));
        parentsMap.put(initState, null);

        Set<GameState> explored = new HashSet<>();
        while (!frontier.isEmpty()) {
            GameState currState = frontier.poll();
            explored.add(currState);
            heuristicMap.remove(currState);
            System.out.print("State: ");
            for (Country c : currState.getWorld().getCountries()) {
                System.out.print(c.getUnits() + "(" + c.getOccupant().getId() + ") ");
            }
            System.out.print("- f = " + heuristic.eval(currState));
            System.out.println();

            if (currState.isFinalState()) {
                return reconstructPath(parentsMap, currState);
            }

            for (GameState passiveNeighbourState : currState.getAllLegalNextStates()) {
                GameState neighbourState = passiveNeighbourState.isFinalState() ? passiveNeighbourState : passiveAgent.getNextState(passiveNeighbourState);

                if (explored.contains(neighbourState)) {
                    continue;
                }

                int neighbourStateHValue = heuristic.eval(neighbourState);
                if (!frontier.contains(neighbourState)) {
                    frontier.add(neighbourState);
                    heuristicMap.put(neighbourState, neighbourStateHValue);
                    parentsMap.put(neighbourState, currState);
                } else if (neighbourStateHValue < heuristicMap.get(neighbourState)) {
                    heuristicMap.remove(neighbourState);
                    heuristicMap.put(neighbourState, neighbourStateHValue);
                    frontier.remove(neighbourState);
                    frontier.add(neighbourState);
                    parentsMap.remove(neighbourState);
                    parentsMap.put(neighbourState, currState);
                }
            }
        }
        return null;
    }

    private List<GameState> reconstructPath(Map<GameState,GameState> parentsMap, GameState currState) {
        List<GameState> path = new ArrayList<>();
        while (currState != null) {
            path.add(currState);
            currState = parentsMap.get(currState);
        }
        Collections.reverse(path);
        return path;
    }

    @Override
    public GameState getNextState(GameState currentState) {
        return this.pathStates.get(currentState.getDepth());
    }
}
