package org.firstinspires.ftc.teamcode.DecodeChallenge.OpModes;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.IntakeController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.PedroPathing.BlueFullRunPathing;
import org.firstinspires.ftc.teamcode.DecodeChallenge.PedroPathing.Constants;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.FireSequenceSystemStateMachine;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.RobotMapping;

import java.nio.file.Path;

@Autonomous (name = "BlueAutoTest", group = "Test")
public class BlueAutoTest extends OpMode {
    private enum PathState { START, ALIGN, LOAD, SHOOT, FINISH}
    private PathState _currentState = PathState.START;

    private Follower _follower;
    private BlueFullRunPathing _bluePathing;
    private FireSequenceSystemStateMachine _fireSequence;
    private IntakeController _intake;

    private ElapsedTime _timeOut;

    private int _currentRow;


    @Override
    public void init() {
        _follower = Constants.createFollower(hardwareMap);
        _bluePathing = new BlueFullRunPathing(_follower);

        RobotMapping _robotMapping = new RobotMapping(hardwareMap);
        _fireSequence = new FireSequenceSystemStateMachine(telemetry, _robotMapping);
        _intake = new IntakeController(_robotMapping.UpperLeftIntake, _robotMapping.UpperRightIntake, _robotMapping.LowerLeftIntake, _robotMapping.LowerRightIntake);

        _timeOut = new ElapsedTime();
        telemetry.addLine("Blue Auto Initialized...");
    }

    @Override
    public void loop() {
        _follower.update();

        switch (_currentState){
            case START:
                _currentRow = 0;
                _fireSequence.InitializeFar();
                _follower.followPath(_bluePathing.PreloadedShoot);
                setPathState(PathState.SHOOT);
                break;

            case SHOOT:
                if (!_follower.isBusy() || _timeOut.seconds() > 1){
                    _fireSequence.UpdateStatus();
                }

                if (!_fireSequence.IsBusy()){
                    _currentRow++;

                    if (_currentRow >= 4){
                        setPathState(PathState.FINISH);
                    } else if (setRowAlignment(_currentRow)) {
                        setPathState(PathState.ALIGN);
                    }
                }
                break;

            case ALIGN:
                if (!_follower.isBusy() || _timeOut.seconds() > 1){
                    _intake.Activate();

                    if (setRowLoad(_currentRow)){
                    setPathState(PathState.LOAD);
                    }
                }
                break;

            case LOAD:
                if (!_follower.isBusy() || _timeOut.seconds() > 1){
                    _intake.Deactivate();

                    if (setRowShoot(_currentRow)){
                        _follower.followPath(_bluePathing.FinishPosition);
                        setPathState(PathState.SHOOT);
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

    private  boolean setRowShoot(int currentRow){
        switch (currentRow){
            case 1:
                _follower.followPath(_bluePathing.FirstRowShoot);
                return true;

            case 2:
                _follower.followPath(_bluePathing.SecondRowShoot);
                return true;

            case 3:
                _follower.followPath(_bluePathing.ThirdRowShoot);
                return true;

            default:
                telemetry.addLine("No Shoot Position");
                return false;
        }
    }

    private boolean setRowLoad(int currentRow){
        switch (currentRow){
            case 1:
                _follower.followPath(_bluePathing.FirstRowLoad);
                return true;

            case 2:
                _follower.followPath(_bluePathing.SecondRowLoad);
                return true;

            case 3:
                _follower.followPath(_bluePathing.ThirdRowLoad);
                return true;

            default:
                telemetry.addLine("No Row To Load");
                return false;
        }
    }

    private boolean setRowAlignment(int currentRow){
        switch (currentRow){
            case 1:
                _follower.followPath(_bluePathing.FirstRowAlign);
                return true;

            case 2:
                _follower.followPath(_bluePathing.SecondRowAlign);
                return true;

            case 3:
                _follower.followPath(_bluePathing.ThirdRowAlign);
                return true;

            default:
                telemetry.addLine("No Row To Align");
                return false;
        }
    }

    private void setPathState(PathState newState) {
        _currentState = newState;
        _timeOut.reset();
    }
}
