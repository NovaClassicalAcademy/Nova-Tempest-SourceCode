package org.firstinspires.ftc.teamcode.DecodeChallenge.OpModes;

import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.IntakeController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.PedroPathing.BlueFullRunPathing;
import org.firstinspires.ftc.teamcode.DecodeChallenge.PedroPathing.Constants;
import org.firstinspires.ftc.teamcode.DecodeChallenge.PedroPathing.RedFullRunPathing;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.FireSequenceSystemStateMachine;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.RobotMapping;

@Autonomous (name = "Red Auto Test", group = "Test")
public class RedAutoTest extends OpMode {
    private enum PathState {START, ALIGN, LOAD, SHOOT, FINISH}

    private PathState _currentState = PathState.START;

    private Follower _follower;
    private RedFullRunPathing _redPathing;
    private FireSequenceSystemStateMachine _fireSequence;
    private IntakeController _intake;

    private ElapsedTime _timeOut;

    private int _currentRow;


    @Override
    public void init() {
        _follower = Constants.createFollower(hardwareMap);
        _redPathing = new RedFullRunPathing(_follower);

        RobotMapping _robotMapping = new RobotMapping(hardwareMap);
        _fireSequence = new FireSequenceSystemStateMachine(telemetry, _robotMapping);
        _intake = new IntakeController(_robotMapping.UpperLeftIntake, _robotMapping.UpperRightIntake, _robotMapping.LowerLeftIntake, _robotMapping.LowerRightIntake);

        _timeOut = new ElapsedTime();
        telemetry.addLine("Red Auto Initialized...");
    }

    @Override
    public void loop() {
        _follower.update();

        switch (_currentState) {
            case START:
                _currentRow = 0;
                _fireSequence.InitFarFireMode();
                SetStartPath(PathState.SHOOT);
                break;

            case SHOOT:
                if (!_follower.isBusy()) {
                    _fireSequence.ProcessFireMode();

                    if (_fireSequence.IsComplete()) {
                        _currentRow++;

                        if (_currentRow < 4) {
                            SetNewPath(GetRowAlignment(_currentRow), PathState.ALIGN);
                        } else {
                            SetEndPath(PathState.FINISH);
                        }
                    }
                }
                break;

            case ALIGN:
                if (!_follower.isBusy()) {
                    _intake.Activate();
                    SetNewPath(GetRowLoad(_currentRow), PathState.LOAD);
                }
                break;

            case LOAD:
                if (!_follower.isBusy()) {
                    _intake.Deactivate();
                    SetNewPath(GetRowShoot(_currentRow), PathState.SHOOT);

                    if (_currentRow < 4) {
                        _fireSequence.InitFarFireMode();
                    }
                }
                break;

            case FINISH:
                telemetry.addLine("Finished Run");
                break;
        }

        telemetry.addData("path state: ", _currentState.toString());
        telemetry.addData("x: ", _follower.getPose().getX());
        telemetry.addData("y: ", _follower.getPose().getY());
        telemetry.addData("heading: ", _follower.getPose().getHeading());
        telemetry.update();
    }

    private PathChain GetRowShoot(int currentRow) {
        PathChain results = null;

        switch (currentRow) {
            case 1:
                results = _redPathing.FirstRowShoot;
                break;

            case 2:
                results = _redPathing.SecondRowShoot;
                break;

            case 3:
                results = _redPathing.ThirdRowShoot;
                break;
        }
        return results;
    }

    private PathChain GetRowLoad(int currentRow){
        PathChain results = null;

        switch (currentRow){
            case 1:
                results = _redPathing.FirstRowLoad;
                break;

            case 2:
                results = _redPathing.SecondRowLoad;
                break;

            case 3:
                results = _redPathing.ThirdRowLoad;
                break;
        }
        return results;
    }

    private PathChain GetRowAlignment(int currentRow){
        PathChain results = null;
        switch (currentRow){
            case 1:
                results = _redPathing.FirstRowAlign;
                break;

            case 2:
                results = _redPathing.SecondRowAlign;
                break;

            case 3:
                results = _redPathing.ThirdRowAlign;
                break;
        }

        return results;
    }
    private void SetNewPath(PathChain newPath, PathState newState){
        _follower.followPath(newPath);
        _currentState = newState;
        _timeOut.reset();
    }

    private void SetStartPath(PathState newState){
        _follower.followPath(_redPathing.PreloadedShoot);
        _currentState = newState;
        _timeOut.reset();
    }

    private void SetEndPath(PathState newState){
        _follower.followPath(_redPathing.FinishPosition);
        _currentState = newState;
        _timeOut.reset();
    }
}
