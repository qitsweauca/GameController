package controller.action.ui;

import common.Log;
import controller.action.ActionBoard;
import controller.action.ActionType;
import controller.action.GCAction;
import data.PlayerInfo;
import data.Rules;
import data.states.AdvancedData;
import data.values.*;


/**
 * @author Michel Bartsch
 * 
 * This action means that a team has scored or it`s score is to be decreased.
 */
public class Goal extends GCAction
{
    /** On which side (0:left, 1:right) */
    private int side;
    /** This value will be added to the score. */
    private int set;
    
    
    /**
     * Creates a new Goal action.
     * Look at the ActionBoard before using this.
     * 
     * @param side      On which side (0:left, 1:right)
     * @param set       This value will be added to the score.
     */
    public Goal(int side, int set)
    {
        super(ActionType.UI);
        this.side = side;
        this.set = set;
    }

    /**
     * Performs this action to manipulate the data (model).
     * 
     * @param data      The current data to work on.
     */
    @Override
    public void perform(AdvancedData data)
    {
        // Before adding the dropin points, dirty penalty serving states for the robots should be cleared
        data.clearPenaltiesServed();
        // If we are in a dropin game we want to count dropin points
        // We are doing this here with forwarding an action to later call the action directly
        // from the button click of a score goal
        if (data.gameType == GameTypes.DROPIN){
            if (this.set > 0){
                new DropInPointsEvaluation(Side.getFromInt(this.side)).perform(data);
            } else {
                new DropInPointsEvaluation(Side.getFromInt(this.side).getOther()).perform(data);
            }
        }

        // Normal goal action
        data.team[side].score += set;
        if (set == 1) {
            if (data.secGameState != SecondaryGameStates.PENALTYSHOOT) {
                data.kickOffTeam = data.team[1 - side].teamNumber;
                data.kickOffReason = AdvancedData.KICKOFF_GOAL;
                Log.setNextMessage("Goal for " + data.team[side].teamColor);
                ActionBoard.ready.perform(data);
            } else {
                data.team[side].singleShots += (1<<(data.team[side].penaltyShot-1));
                data.kickOffReason = AdvancedData.KICKOFF_PENALTYSHOOT;
                Log.setNextMessage("Goal for " + data.team[side].teamColor);
                ActionBoard.finish.perform(data);
            }
        } else {
            Log.state(data, "Goal decrease for " + data.team[side].teamColor);
        }
    }
    
    /**
     * Checks if this action is legal with the given data (model).
     * Illegal actions are not performed by the EventHandler.
     * 
     * @param data      The current data to check with.
     */
    @Override
    public boolean isLegal(AdvancedData data)
    {
        return ((set == 1)
              && (data.gameState == GameStates.PLAYING)
              && ( (data.secGameState != SecondaryGameStates.PENALTYSHOOT)
                || (data.kickOffTeam == data.team[side].teamNumber)) )
            || data.testmode;
    }
}