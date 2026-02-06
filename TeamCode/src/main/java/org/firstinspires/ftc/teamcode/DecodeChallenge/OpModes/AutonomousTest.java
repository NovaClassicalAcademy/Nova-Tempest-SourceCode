//package org.firstinspires.ftc.teamcode.DecodeChallenge.OpModes;
//
//import com.pedropathing.follower.Follower;
//import com.pedropathing.geometry.BezierLine;
//import com.pedropathing.geometry.Pose;
//import com.pedropathing.paths.PathChain;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import org.firstinspires.ftc.teamcode.DecodeChallenge.AllianceColor;
//import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.IntakeController;
//import org.firstinspires.ftc.teamcode.DecodeChallenge.Paths.BluePathLibrary;
////import org.firstinspires.ftc.teamcode.DecodeChallenge.Paths.RedPathLibrary;
////import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.DecodeDriveSystemStateMachine;
//import org.firstinspires.ftc.teamcode.DecodeChallenge.Paths.TestPath;
//import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.RobotMapping;
//import org.firstinspires.ftc.teamcode.DecodeChallenge.PedroPathing.Constants;
//import org.firstinspires.ftc.teamcode.DecodeChallenge.Systems.FireSequenceSystemStateMachine;
//
//@Autonomous(name="Autonomous Comp Test", group="Test")
//public class AutonomousTest extends LinearOpMode {
//    private enum RobotState {Start, Preloaded, Return, MoveOffWall, MoveOffLaunch, Idle}
//
//    private RobotState _currentAutoState = RobotState.Start;
//    private RobotMapping _robotMapping;
////    private DecodeDriveSystemStateMachine _pathing;
//    private FireSequenceSystemStateMachine _fireSequence;
//    private IntakeController _intake;
//    private TestPath _pathTest;
//    private Follower _follower;
//    private ElapsedTime _stateTimer;
//    private AllianceColor _allianceColor = AllianceColor.Blue;
//
//    @Override
//    public void runOpMode() {
//
//        _robotMapping = new RobotMapping(hardwareMap);
//        _follower = Constants.createFollower(hardwareMap);
//
////        _pathing = new DecodeDriveSystemStateMachine(telemetry, _follower, _robotMapping, _allianceColor);
////        _pathing.Init();
//
//        _pathTest = new TestPath(_follower);
//
//        _stateTimer = new ElapsedTime();
//
//        _intake = new IntakeController(_robotMapping.UpperLeftIntake, _robotMapping.UpperRightIntake, _robotMapping.LowerLeftIntake, _robotMapping.LowerRightIntake);
////        _fireSequence = new FireSequenceSystemStateMachine(telemetry, _robotMapping);
//
//        telemetry.addData("OpMode", "Autonomous Comp Test 14");
//        telemetry.update();
//
//        waitForStart();
//
//        // NOTE: initialize launch variable and spin up the launcher.
////        FireSequenceSystemStateMachine.LaunchState launchState;
//
//        while (opModeIsActive()) {
//            _follower.update();
//
//            switch (_currentAutoState){
//
//                case Start:
//                    if (!_follower.isBusy()) {
//                        _follower.followPath(_pathTest.Path1);
//                        _currentAutoState = RobotState.Preloaded;
//                        _stateTimer.reset();
//                    }
//                    break;
//
//                case Preloaded:
//                    if (!_follower.isBusy() && _stateTimer.seconds() > 10) {
//                        _follower.followPath(_pathTest.Path2);
//                        _currentAutoState = RobotState.Idle;
//                        _stateTimer.reset();
//                    }
//                    break;
////
////                case Return:
////                    if (!_follower.isBusy() && _stateTimer.seconds() > 10) {
////                        _follower.followPath(_pathTest.);
////                        _currentAutoState = RobotState.Idle;
////                    }
////                    break;
//
//                case Idle:
//                    break;
//            }
//
//            telemetry.addData("x", _follower.getPose().getX());
//            telemetry.addData("y", _follower.getPose().getY());
//            telemetry.addData("heading", _follower.getPose().getHeading());
//            telemetry.addData("isBusy", _follower.isBusy());
//            telemetry.update();
////
////            _follower.update();
////
////            switch (_currentAutoState) {
////                case Start:
////                    if (step == 1) {
////                        if (!_follower.isBusy()) {
////                            _pathing.SetRowDestination(1);
////                            step = 2;
////                            _stateTimer.reset();
////
////                            telemetry.addData("In Step", "1");
////                        }
////                    }
////                    break;
////
////                case Idle:
////                    telemetry.addData("In Step", "Finish");
////                    _follower.holdPoint(_follower.getPose());
////                    telemetry.addData("Status", "Holding Position");
////                    break;
////            }
////
////            telemetry.update();
////
////        }
////    }
//        }
//    }
//}