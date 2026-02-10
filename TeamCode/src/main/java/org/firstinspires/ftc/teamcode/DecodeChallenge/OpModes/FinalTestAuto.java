package org.firstinspires.ftc.teamcode.DecodeChallenge.OpModes;

import com.pedropathing.follower.Follower;
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
        ALIGN_TO_ROW_1,
        ALIGNED_COMPLETE_1,
        LOADING_ROW_1,
        LOAD_COMPLETE_1,
        RETURN_HOME_1,
        CHECK_AT_HOME_1,

        PATH_TO_LOAD_ROW_2,
        PATH_TO_ROW_2_COMPLETE,
        ALIGN_TO_ROW_2,
        ALIGNED_COMPLETE_2,
        LOADING_ROW_2,
        LOAD_COMPLETE_2,
        RETURN_HOME_2,
        CHECK_AT_HOME_2,


        PATH_TO_LOAD_ROW_3,
        PATH_TO_ROW_3_COMPLETE,
        ALIGN_TO_ROW_3,
        ALIGNED_COMPLETE_3,
        LOADING_ROW_3,
        LOAD_COMPLETE_3,
        RETURN_HOME_3,
        CHECK_AT_HOME_3,

        MOVE_OUT_OF_LAUNCH, //THIS WILL HOLD THE POSITION
        FINISHED; //THIS WILL OUTPUT TELEMETRY TO TELL US WE'RE DONE FS
    }

    PathState _pathstate;

    //------------Positions------------
    private final Pose startPosition = new Pose(60.097, 9.229, Math.toRadians(0));

    private final Pose posRow1 = new Pose(60.097,40.147937411095285, Math.toRadians(0));
    private final Pose alignPosToRow1 = new Pose(39.73826458036982, 40.147937411095285, Math.toRadians(0));
    private final Pose intakePosRow1 = new Pose(13.391180654338546, 40.147937411095285, Math.toRadians(0));

    private final Pose posRow2 = new Pose(60.097, 60.42674253200569, Math.toRadians(0));
    private final Pose alignPosToRow2 = new Pose(42.71692745376955, 60.42674253200569);
    private final Pose intakePosRow2 = new Pose(12.897581792318634, 60.42674253200569);

    private final Pose posRow3 = new Pose(60.097, 83.71692745376957, Math.toRadians(0));
    private final Pose alignPosToRow3 = new Pose(42.15647226173542, 83.71692745376957, Math.toRadians(0));
    private final Pose intakePosRow3 = new Pose(12.450924608819347, 83.71692745376957, Math.toRadians(0));

    private final Pose shootPosition = new Pose(60.097, 9.229, Math.toRadians(110)); //front is facing the basket. see how we need to switch sides.
    private final Pose restPosition = new Pose(49.60597439544809, 33.184921763869134, Math.toRadians(0));

    //-----------PathChains-----------
    private PathChain driveToRow1;
    private PathChain alignToRow1;
    private PathChain intakeRow1;
    private PathChain returnHomeFrom1;

    private PathChain driveToRow2;
    private PathChain alignToRow2;
    private PathChain intakeRow2;
    private PathChain returnHomeFrom2;

    private PathChain driveToRow3;
    private PathChain alignToRow3;
    private PathChain intakeRow3;
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
                .addPath(new BezierLine(startPosition, posRow1))
                .setLinearHeadingInterpolation(startPosition.getHeading(), posRow1.getHeading())
                .build();

        alignToRow1 = _follower.pathBuilder()
                .addPath(new BezierLine(posRow1, alignPosToRow1))
                .setLinearHeadingInterpolation(posRow1.getHeading(), alignPosToRow1.getHeading())
                .build();

        intakeRow1 = _follower.pathBuilder()
                .addPath(new BezierLine(alignPosToRow1, intakePosRow1))
                .setLinearHeadingInterpolation(alignPosToRow1.getHeading(), intakePosRow1.getHeading())
                .build();

        returnHomeFrom1 = _follower.pathBuilder()
                .addPath(new BezierLine(intakePosRow1, shootPosition))
                .setLinearHeadingInterpolation(alignPosToRow1.getHeading(), shootPosition.getHeading())
                .build();

        driveToRow2 = _follower.pathBuilder()
                .addPath(new BezierLine(shootPosition, posRow2))
                .setLinearHeadingInterpolation(shootPosition.getHeading(), posRow2.getHeading())
                .build();

        alignToRow2 = _follower.pathBuilder()
                .addPath(new BezierLine(posRow2, alignPosToRow2))
                .setLinearHeadingInterpolation(posRow2.getHeading(), alignPosToRow2.getHeading())
                .build();

        intakeRow2 = _follower.pathBuilder()
                .addPath(new BezierLine(alignPosToRow2, intakePosRow2))
                .setLinearHeadingInterpolation(alignPosToRow2.getHeading(), intakePosRow2.getHeading())
                .build();

        returnHomeFrom2 = _follower.pathBuilder()
                .addPath(new BezierLine(intakePosRow2, shootPosition))
                .setLinearHeadingInterpolation(intakePosRow2.getHeading(), shootPosition.getHeading())
                .build();

        driveToRow3 = _follower.pathBuilder()
                .addPath(new BezierLine(shootPosition, posRow3))
                .setLinearHeadingInterpolation(shootPosition.getHeading(), posRow3.getHeading())
                .build();

        alignToRow3 = _follower.pathBuilder()
                .addPath(new BezierLine(posRow3, alignPosToRow3))
                .setLinearHeadingInterpolation(posRow3.getHeading(), alignPosToRow3.getHeading())
                .build();

        intakeRow3 = _follower.pathBuilder()
                .addPath(new BezierLine(alignPosToRow3, intakePosRow3))
                .setLinearHeadingInterpolation(alignPosToRow3.getHeading(), intakePosRow3.getHeading())
                .build();

        returnHomeFrom3 = _follower.pathBuilder()
                .addPath(new BezierLine(intakePosRow3, shootPosition))
                .setLinearHeadingInterpolation(intakePosRow3.getHeading(), shootPosition.getHeading())
                .build();

        moveOutOfLaunch = _follower.pathBuilder()
                .addPath(new BezierLine(shootPosition, restPosition))
                .setLinearHeadingInterpolation(shootPosition.getHeading(), restPosition.getHeading())
                .build();
    }

    @Override
    public void loop() {
        _follower.update();

        switch (_pathstate){

            case PATH_TO_LOAD_ROW_1:
                _follower.followPath(driveToRow1);
                setPathState(PathState.PATH_TO_ROW_1_COMPLETE);
                break;

            case PATH_TO_ROW_1_COMPLETE:
                if (!_follower.isBusy() && _pathTimer.getElapsedTimeSeconds() > 0.1) {
                    setPathState(PathState.ALIGN_TO_ROW_1);
                }
                break;

            case ALIGN_TO_ROW_1:
                _follower.followPath(alignToRow1);
                setPathState(PathState.ALIGNED_COMPLETE_1);
                break;

            case ALIGNED_COMPLETE_1:
                if (!_follower.isBusy() && _pathTimer.getElapsedTimeSeconds() > 0.1) {
                    setPathState(PathState.LOADING_ROW_1);
                }
                break;

            case LOADING_ROW_1:
                _follower.followPath(intakeRow1);
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
                _follower.followPath(driveToRow2);
                setPathState(PathState.PATH_TO_ROW_2_COMPLETE);
                break;

            case PATH_TO_ROW_2_COMPLETE:
                if (!_follower.isBusy() && _pathTimer.getElapsedTimeSeconds() > 0.1) {
                    setPathState(PathState.ALIGN_TO_ROW_2);
                }
                break;

            case ALIGN_TO_ROW_2:
                _follower.followPath(alignToRow2);
                setPathState(PathState.ALIGNED_COMPLETE_2);
                break;

            case ALIGNED_COMPLETE_2:
                if (!_follower.isBusy() && _pathTimer.getElapsedTimeSeconds() > 0.1) {
                    setPathState(PathState.LOADING_ROW_2);
                }
                break;

            case LOADING_ROW_2:
                _follower.followPath(intakeRow2);
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
                _follower.followPath(driveToRow3);
                setPathState(PathState.PATH_TO_ROW_3_COMPLETE);
                break;

            case PATH_TO_ROW_3_COMPLETE:
                if (!_follower.isBusy() && _pathTimer.getElapsedTimeSeconds() > 0.1) {
                    setPathState(PathState.ALIGN_TO_ROW_3);
                }
                break;

            case ALIGN_TO_ROW_3:
                _follower.followPath(alignToRow3);
                setPathState(PathState.ALIGNED_COMPLETE_3);
                break;

            case ALIGNED_COMPLETE_3:
                if (!_follower.isBusy() && _pathTimer.getElapsedTimeSeconds() > 0.1) {
                    setPathState(PathState.LOADING_ROW_3);
                }
                break;

            case LOADING_ROW_3:
                _follower.followPath(intakeRow3);
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
