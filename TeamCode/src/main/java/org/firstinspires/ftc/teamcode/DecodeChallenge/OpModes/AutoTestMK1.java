package org.firstinspires.ftc.teamcode.DecodeChallenge.OpModes;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.DistanceSensorController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.PedroPathing.Constants;


@Autonomous (name="AutonMK1", group="Test")
public class AutoTestMK1 extends OpMode {
    private Follower _follower;
    private Timer _pathTimer, _opModeTimer;

//    private DistanceSensorController RearDistSensor;
//    private DistanceSensorController FrontDistSensor;


    public enum PathState {
        STARTPOS_SHOOTPOS,
        SHOOT_AND_PRELOAD;

    }

    PathState _pathstate;

    private final Pose startPositon = new Pose(59.27738264580369, 60.375533428165, Math.toRadians(0));
    private final Pose shootPosition = new Pose(59.27738264580369, 79.674, Math.toRadians(0));

    private PathChain driveStatePosShootPos;

    @Override
    public void init() {
        _pathstate = PathState.STARTPOS_SHOOTPOS;
        _follower = Constants.createFollower(hardwareMap);
        _pathTimer = new Timer();
        _opModeTimer = new Timer();
        _opModeTimer.resetTimer();

        BuildPaths();
        _follower.setPose(startPositon);
    }

    public void start(){
        _opModeTimer.resetTimer();
        setPathState(_pathstate);
    }

    @Override
    public void loop() {
        _follower.update();
        statePathUpdate();

        telemetry.addData("path state: ", _pathstate.toString());
        telemetry.addData("x: ", _follower.getPose().getX());
        telemetry.addData("y: ", _follower.getPose().getY());
        telemetry.addData("heading: ", _follower.getPose().getHeading());
        telemetry.addData("path time: ", _pathTimer.getElapsedTimeSeconds());
    }

    public void BuildPaths(){
        driveStatePosShootPos = _follower.pathBuilder()
                .addPath(new BezierLine(startPositon, shootPosition))
                .setLinearHeadingInterpolation(startPositon.getHeading(), shootPosition.getHeading())
                .build();
    }

    public void statePathUpdate(){
        switch (_pathstate){
            case STARTPOS_SHOOTPOS:
                _follower.followPath(driveStatePosShootPos, true);
                setPathState(PathState.SHOOT_AND_PRELOAD);
                break;

            case SHOOT_AND_PRELOAD:
                if(!_follower.isBusy()){
                    telemetry.addLine("Done with Path 1");
                }
                break;

            default:
                telemetry.addLine("No State Commanded");
                break;
        }

    }

    public void setPathState(PathState newState){
        _pathstate = newState;
        _pathTimer.resetTimer();
    }
}
