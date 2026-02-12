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

import org.firstinspires.ftc.teamcode.DecodeChallenge.PedroPathing.Constants;

@Autonomous (name = "FinalAutoTest", group = "Test")
public class FinalTestAuto extends OpMode {
    private Follower _follower;
    private Timer _pathTimer, _opModeTimer;
    public enum PathState {
        PATH_TO_LOAD_ROW_1,
        PATH_TO_ROW_1_COMPLETE,
        LOADING_ROW_1,
        LOAD_COMPLETE_1,
        RETURN_HOME_1,
        CHECK_AT_HOME_1,

        PATH_TO_LOAD_ROW_2,
        PATH_TO_ROW_2_COMPLETE,
        LOADING_ROW_2,
        LOAD_COMPLETE_2,
        RETURN_HOME_2,
        CHECK_AT_HOME_2,


        PATH_TO_LOAD_ROW_3,
        PATH_TO_ROW_3_COMPLETE,
        LOADING_ROW_3,
        LOAD_COMPLETE_3,
        RETURN_HOME_3,
        CHECK_AT_HOME_3,

        MOVE_OUT_OF_LAUNCH, //THIS WILL HOLD THE POSITION
        FINISHED; //THIS WILL OUTPUT TELEMETRY TO TELL US WE'RE DONE FS
    }

    PathState _pathstate;

    //------------Positions------------
    private final Pose startPosition = new Pose(58.244, 11.179, Math.toRadians(90));

    private final Pose posRow1 = new Pose(40.510,35.111, Math.toRadians(180));
    private final Pose intakePosRow1 = new Pose(12.928, 35.111, Math.toRadians(180));

    private final Pose posRow2 = new Pose(58.104, 11.301, Math.toRadians(90));
    private final Pose intakePosRow2 = new Pose(9.939, 60.318, Math.toRadians(180));

    private final Pose posRow3 = new Pose(43.750, 84.518, Math.toRadians(90));
    private final Pose intakePosRow3 = new Pose(11.408, 84.093, Math.toRadians(180));

    private final Pose shootPosition = new Pose(57.922, 11.488, Math.toRadians(110)); //front is facing the basket. see how we need to switch sides.
    private final Pose restPosition = new Pose(57.922, 32.696, Math.toRadians(90));

    //-----------PathChains-----------
    private PathChain driveToRow1;
    private PathChain loadRow1;
    private PathChain returnHomeFrom1;

    private PathChain driveToRow2;
    private PathChain loadRow2;
    private PathChain returnHomeFrom2;

    private PathChain driveToRow3;
    private PathChain loadRow3;
    private PathChain returnHomeFrom3;

    private PathChain moveOutOfLaunch;

    //-----------BuildingPaths-----------

    @Override
    public void init() {
        _pathstate = PathState.PATH_TO_LOAD_ROW_1;
        _follower = Constants.createFollower(hardwareMap);
        _pathTimer = new Timer();
        _opModeTimer = new Timer();
        _opModeTimer.resetTimer();

        BuildPaths();
        _follower.setPose(startPosition);

    }

    public void BuildPaths(){
        driveToRow1 = _follower.pathBuilder()
                .addPath(new BezierCurve(
                        new Pose(startPosition.getX(), startPosition.getY()),
                        new Pose(73.754, 37.191),
                        new Pose(posRow1.getX(), posRow1.getY()))
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .setVelocityConstraint(12)
                .build();

        loadRow1 = _follower.pathBuilder()
                .addPath(new BezierLine(posRow1, intakePosRow1))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .setVelocityConstraint(12)
                .build();

        returnHomeFrom1 = _follower.pathBuilder()
                .addPath(new BezierLine(intakePosRow1, shootPosition))
                .setConstantHeadingInterpolation(Math.toRadians(110))
                .build();

        driveToRow2 = _follower.pathBuilder()
                .addPath(new BezierCurve(
                        new Pose(shootPosition.getX(), shootPosition.getY()),
                        new Pose(84.039, 59.839), ///TODO: NEED TO RESTART PATH
                        new Pose(posRow2.getX(), posRow2.getY())))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .setVelocityConstraint(12)
                .build();

        loadRow2 = _follower.pathBuilder()
                .addPath(new BezierLine(posRow2, intakePosRow2))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .setVelocityConstraint(12)
                .build();

        returnHomeFrom2 = _follower.pathBuilder()
                .addPath(new BezierLine(intakePosRow2, shootPosition))
                .setConstantHeadingInterpolation(Math.toRadians(110))
                .build();

        driveToRow3 = _follower.pathBuilder()
                .addPath(new BezierCurve(
                        new Pose(shootPosition.getX(), shootPosition.getY()),
                        new Pose(83.630, 85.0),
                        new Pose(posRow3.getX(), posRow3.getY())))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .setVelocityConstraint(12)
                .build();

        loadRow3 = _follower.pathBuilder()
                .addPath(new BezierLine(posRow3, intakePosRow3))
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .setVelocityConstraint(12)
                .build();

        returnHomeFrom3 = _follower.pathBuilder()
                .addPath(new BezierLine(intakePosRow3, shootPosition))
                .setConstantHeadingInterpolation(Math.toRadians(110))
                .build();

        moveOutOfLaunch = _follower.pathBuilder()
                .addPath(new BezierLine(shootPosition, restPosition))
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();
    }

    @Override
    public void loop() {
        _follower.update();

        switch (_pathstate){

            case PATH_TO_LOAD_ROW_1:
                _follower.followPath(driveToRow1, true);
                setPathState(PathState.PATH_TO_ROW_1_COMPLETE);
                break;

            case PATH_TO_ROW_1_COMPLETE:
                if (!_follower.isBusy() && _pathTimer.getElapsedTimeSeconds() > 0.1) {
                    setPathState(PathState.LOADING_ROW_1);
                }
                break;

            case LOADING_ROW_1:
                _follower.followPath(loadRow1, true);

                setPathState(PathState.LOAD_COMPLETE_1);
                break;

            case LOAD_COMPLETE_1:
                if (!_follower.isBusy() && _pathTimer.getElapsedTimeSeconds() > 0.1) {
                    setPathState(PathState.RETURN_HOME_1);
                }
                break;

            case RETURN_HOME_1:
                _follower.followPath(returnHomeFrom1);
                setPathState(PathState.CHECK_AT_HOME_1);
                break;

            case CHECK_AT_HOME_1:
                if (!_follower.isBusy() && _pathTimer.getElapsedTimeSeconds() > 0.1) {
                    setPathState(PathState.PATH_TO_LOAD_ROW_2);
                }
                break;

            case PATH_TO_LOAD_ROW_2:
                _follower.followPath(driveToRow2, true);
                setPathState(PathState.PATH_TO_ROW_2_COMPLETE);
                break;

            case PATH_TO_ROW_2_COMPLETE:
                if (!_follower.isBusy() && _pathTimer.getElapsedTimeSeconds() > 0.1) {
                    setPathState(PathState.LOADING_ROW_2);
                }
                break;

            case LOADING_ROW_2:
                _follower.followPath(loadRow2, true);
                setPathState(PathState.LOAD_COMPLETE_2);
                break;

            case LOAD_COMPLETE_2:
                if (!_follower.isBusy() && _pathTimer.getElapsedTimeSeconds() > 0.1) {
                    setPathState(PathState.RETURN_HOME_2);
                }
                break;

            case RETURN_HOME_2:
                _follower.followPath(returnHomeFrom2);
                setPathState(PathState.CHECK_AT_HOME_2);
                break;

            case CHECK_AT_HOME_2:
                if (!_follower.isBusy() && _pathTimer.getElapsedTimeSeconds() > 0.1) {
                    setPathState(PathState.PATH_TO_LOAD_ROW_3);
                }
                break;

            case PATH_TO_LOAD_ROW_3:
                _follower.followPath(driveToRow3, true);
                setPathState(PathState.PATH_TO_ROW_3_COMPLETE);
                break;

            case PATH_TO_ROW_3_COMPLETE:
                if (!_follower.isBusy() && _pathTimer.getElapsedTimeSeconds() > 0.1) {
                    setPathState(PathState.LOADING_ROW_3);
                }
                break;

            case LOADING_ROW_3:
                _follower.followPath(loadRow3, true);
                setPathState(PathState.LOAD_COMPLETE_3);
                break;

            case LOAD_COMPLETE_3:
                if (!_follower.isBusy() && _pathTimer.getElapsedTimeSeconds() > 0.1) {
                    setPathState(PathState.RETURN_HOME_3);
                }
                break;

            case RETURN_HOME_3:
                _follower.followPath(returnHomeFrom3);
                setPathState(PathState.CHECK_AT_HOME_3);
                break;

            case CHECK_AT_HOME_3:
                if (!_follower.isBusy() && _pathTimer.getElapsedTimeSeconds() > 0.1) {
                    setPathState(PathState.MOVE_OUT_OF_LAUNCH);
                }
                break;

            case MOVE_OUT_OF_LAUNCH:
                _follower.followPath(moveOutOfLaunch, true);
                setPathState(PathState.FINISHED);
                break;

            case FINISHED:
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
}
