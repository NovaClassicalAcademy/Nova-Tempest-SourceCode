package org.firstinspires.ftc.teamcode.DecodeChallenge.OpModes;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.DistanceSensorController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.PedroPathing.Constants;


@Autonomous (name="AutoMK2", group="Test")
public class AutoTestMK2 extends OpMode {
    private Follower _follower;
    private Timer _pathTimer, _opModeTimer;

//    private DistanceSensorController RearDistSensor;
//    private DistanceSensorController FrontDistSensor;


    public enum PathState {
        STARTPOS_SHOOTPOS,
        SHOOT_AND_PRELOAD,
        FINAL_MOVEMENT,
        FINAL_POS,
        DRUMROLL,
        CHECK_HOLD;

    }

    PathState _pathstate;

    private final Pose startPositon = new Pose(59.486, 11.277, Math.toRadians(90));
    private final Pose shootPosition = new Pose(56, 37.04721030042918, Math.toRadians(180));
    private final Pose turnedPosition = new Pose(42, 68.58803777800841, Math.toRadians(180));
    private final Pose finalPosition = new Pose(9.328591749644376, 68.58803777800841, Math.toRadians(180));

    private PathChain driveStatePosShootPos;
    private PathChain turn;
    private PathChain moveRight;

    public void BuildPaths() {
        driveStatePosShootPos = _follower.pathBuilder()
                .addPath(new BezierLine(startPositon, shootPosition))
                .setLinearHeadingInterpolation(startPositon.getHeading(), shootPosition.getHeading())
                .build();

        turn = _follower.pathBuilder()
                .addPath(new BezierCurve(
                        new Pose (startPositon.getX(), startPositon.getY()),
                        new Pose(50.771, 49.769),
                        new Pose (turnedPosition.getX(), turnedPosition.getY())
                ))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        moveRight = _follower.pathBuilder()
                .addPath(new BezierLine(turnedPosition,finalPosition))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
    }

    @Override
    public void loop() {

        _follower.update();

        switch (_pathstate) {

            case STARTPOS_SHOOTPOS:
                _follower.followPath(driveStatePosShootPos);

                setPathState(PathState.SHOOT_AND_PRELOAD);
                break;

            case SHOOT_AND_PRELOAD:
                if (!_follower.isBusy() && _pathTimer.getElapsedTimeSeconds() > 0.1) {
                    telemetry.addLine("Done with Path 1");
                    setPathState(PathState.FINAL_MOVEMENT);
                }
                break;

            case FINAL_MOVEMENT:
                _follower.followPath(turn, true);
                setPathState(PathState.FINAL_POS);
                break;

            case FINAL_POS:
                telemetry.addLine("Done with Path 2");
                setPathState(PathState.DRUMROLL);

            case DRUMROLL:
                _follower.followPath(moveRight, true);
                setPathState(PathState.CHECK_HOLD);
                break;

            case CHECK_HOLD:
                telemetry.addLine("Finsihed Route");
                break;


            default:
                telemetry.addLine("No State Commanded");
                break;
        }

        telemetry.addData("path state: ", _pathstate.toString());
        telemetry.addData("x: ", _follower.getPose().getX());
        telemetry.addData("y: ", _follower.getPose().getY());
        telemetry.addData("heading: ", _follower.getPose().getHeading());
        telemetry.addData("path time: ", _pathTimer.getElapsedTimeSeconds());

    }

    public void setPathState(PathState newState) {
        _pathstate = newState;
        _pathTimer.resetTimer();
    }

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

    public void start() {
        _opModeTimer.resetTimer();
        setPathState(_pathstate);
    }
}
