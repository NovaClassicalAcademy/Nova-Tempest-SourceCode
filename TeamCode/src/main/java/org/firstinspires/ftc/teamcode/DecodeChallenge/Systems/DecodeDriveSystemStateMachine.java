package org.firstinspires.ftc.teamcode.DecodeChallenge.Systems;

import android.graphics.Paint;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.DecodeChallenge.AllianceColor;
import org.firstinspires.ftc.teamcode.DecodeChallenge.AprilTagConstant;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.AprilTagDetectionController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.CameraController;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;

import java.util.ArrayList;
import java.util.List;

public class DecodeDriveSystemStateMachine {
    public enum DriveState { DecdingNextSpeciment, Scan, DriveToRow, WaitForLoadTrigger, LoadingSpecimens, LoadingComplete, DriveToLaunch, ReadyToFire, Idle }
    private final Telemetry _telemetry;
    private final Follower _follower;
    private final AprilTagDetectionController _camera;
    private final CameraController _cameraConrtroller;
    private final AllianceColor _allianceColor;

    private PathChain _pathAlignToFirstRow;
    private PathChain _pathIntakeRow1;
    private PathChain _pathReturnFirstRowToLaunch;
    private PathChain _pathAlignToSecondRow;
    private PathChain _pathIntakeRow2;
    private PathChain _pathReturnSecondRowToLaunch;
    private PathChain _pathAlignToThirdRow;
    private PathChain _pathIntakeRow3;
    private PathChain _pathReturnThirdRowToLaunch;
    private PathChain _moveOutOfLaunch;

    private Pose _startPose;
    private List<Integer> _completedSpecimens = new ArrayList<>();
    private int _targetSpecimenId;
    private int _startingSpecimen;
    private DriveState _currentDriveState = DriveState.Idle;
    private boolean _loadTriggerPulled = false;
    private boolean _returnTriggerPulled = false;

    public DecodeDriveSystemStateMachine(Telemetry telemetry, Follower follower, RobotMapping rm, AllianceColor allianceColor) {
        _telemetry = telemetry;
        _follower = follower;
        _allianceColor = allianceColor;
        _camera = new AprilTagDetectionController();
        _cameraConrtroller = new CameraController(_telemetry, rm.Webcam, _allianceColor);
    }

    public void Init(){
//        int startingSpecimen = _camera.GetAprilTag();
//
//        if (startingSpecimen != -1) {
//            _startingSpecimen = startingSpecimen;
//        } else {
//            _startingSpecimen = AprilTagConstant.GPP;
//        }
//
//        Pose startPosition = _cameraConrtroller.GetRobotPose();
//
//        if (startPosition == null) {
//
//            if (_allianceColor != null) {
//                switch (_allianceColor) {
//                    case Blue:
//                        startPosition = new Pose(65.138, 8.603);
//                        break;
//
//                    case Red:
//                        startPosition = new Pose(82.631, 11.471);
//                        break;
//                }
//            }
//        }
//
//        _follower.setStartingPose(startPosition);
//
//        BuildPaths();
        long startTime = System.currentTimeMillis();
        Pose startPosition = null;

        while (startPosition == null && (System.currentTimeMillis() - startTime) < 3000) {
            startPosition = _cameraConrtroller.GetRobotPose();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {}
        }

        if (startPosition == null){
            if (_allianceColor == AllianceColor.Blue){
                startPosition = new Pose(60.506, 11.073);
            }
            else if (_allianceColor == AllianceColor.Red){
                startPosition = new Pose(82.631, 11.471, Math.toRadians(180));
            }

            _telemetry.addLine("Camera Timeout: Using Defult Pose");
        } else {
            _telemetry.addLine("Camera Initialized: Pose Locked");
        }

        _follower.setStartingPose(startPosition);
        _telemetry.update();
        BuildPaths();
    }
    public void ScanNextSpecimen(){
        if (_currentDriveState == DriveState.ReadyToFire){
            MarkSpecimentComplete(_targetSpecimenId);
        }

        ChangeState(DriveState.DecdingNextSpeciment);
    }
    public void StartLoading(){
        _loadTriggerPulled = true;
    }

    public void ReturnToLaunch(){
        _returnTriggerPulled = true;
    }

    public boolean IsWaitingToLoad(){
        return _currentDriveState == DriveState.WaitForLoadTrigger;
    }

    public boolean IsLoadComplete(){
        return _currentDriveState == DriveState.LoadingComplete;
    }

    public boolean IsReadyToFire(){
        return _currentDriveState == DriveState.ReadyToFire;
    }

    private void BuildPaths(){

        _pathAlignToFirstRow = _follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(60.506, 11.073),
                                new Pose(57.886, 37.997),
                                new Pose(44.613, 34.822)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                .build();


        _pathIntakeRow1 = _follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(44.613, 34.822),

                                new Pose(17.183, 35.543)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        _pathReturnFirstRowToLaunch = _follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(17.183, 35.543),

                                new Pose(60.454, 10.723)
                        )
                ).setConstantHeadingInterpolation(Math.toRadians(110))
                .setReversed()
                .build();

        _pathAlignToSecondRow = _follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(55.995, 7.909),
                                new Pose(60.020, 59.855),
                                new Pose(40.939, 59.853)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();

        _pathIntakeRow2 = _follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(40.939, 59.853),

                                new Pose(13.015, 60.431)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();

        _pathReturnSecondRowToLaunch = _follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(13.015, 60.431),

                                new Pose(55.970, 7.797)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();

        _pathAlignToThirdRow = _follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(55.970, 7.797),
                                new Pose(73.340, 84.183),
                                new Pose(43.391, 83.716)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();

        _pathIntakeRow3 = _follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(43.391, 83.716),

                                new Pose(11.914, 84.051)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();

        _pathReturnThirdRowToLaunch = _follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(11.914, 84.051),

                                new Pose(56.249, 7.934)
                        )
                ).setTangentHeadingInterpolation()
                .setReversed()
                .build();

        _moveOutOfLaunch = _follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(61.531, 6.361),

                                new Pose(61.861, 33.183)
                        )
                ).setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                .setReversed()
                .build();
    }

    public void AlignToFirstRow(int _targetSpecimenId){
        _follower.followPath(_pathAlignToFirstRow);
    }

    public void IntakeFirstRow(){
        _follower.followPath(_pathIntakeRow1);
    }

    public void ReturnFirstRowToLaunch(){
        _follower.followPath(_pathReturnFirstRowToLaunch);
    }

    public void AlignToSecondRow(){
        _follower.followPath(_pathAlignToSecondRow);
    }

    public void IntakeSecondRow() { _follower.followPath(_pathIntakeRow2); }

    public void ReturnSecondRowToLaunch(){
        _follower.followPath(_pathReturnSecondRowToLaunch);
    }

    public void AlignToThirdRow(){
        _follower.followPath(_pathAlignToThirdRow);
    }

    public void IntakeThirdRow() {
        _follower.followPath(_pathIntakeRow3);
    }

    public void ReturnThirdRowToLaunch(){
        _follower.followPath(_pathReturnThirdRowToLaunch);
    }

    public void MoveOutOfLaunch() {
        _follower.followPath(_moveOutOfLaunch);
    }

    private int IdentifyNextSpecimen(){

        if (!_completedSpecimens.contains(_startingSpecimen)) {
            return _startingSpecimen;
        }

        if (!_completedSpecimens.contains(AprilTagConstant.GPP)){
            return AprilTagConstant.GPP;
        }

        if (!_completedSpecimens.contains(AprilTagConstant.PGP)){
            return AprilTagConstant.PGP;
        }

        if (!_completedSpecimens.contains(AprilTagConstant.PPG)){
            return AprilTagConstant.PPG;
        }

        return -1;
    }

    private void MarkSpecimentComplete(int specimentId){
        _completedSpecimens.add(specimentId);
    }

    private void ChangeState(DriveState newState){
        _currentDriveState = newState;
        // NOTE: If you need to do something when the state changes, do it here:
    }

    private void SetRowDestination(int targetSpecimenId){
        switch (targetSpecimenId){
            case AprilTagConstant.GPP:
                AlignToFirstRow(_targetSpecimenId);
                break;

            case AprilTagConstant.PGP:
                AlignToSecondRow();
                break;

            case AprilTagConstant.PPG:
                AlignToThirdRow();
                break;

            default:
                ChangeState(DriveState.Idle);
        }
    }

    private void SetIntakeRoute(int targetSpecimenId){
        switch (targetSpecimenId){

            case AprilTagConstant.GPP:
                IntakeFirstRow();
                break;

            case AprilTagConstant.PGP:
                IntakeSecondRow();
                break;

            case AprilTagConstant.PPG:
                IntakeThirdRow();
                break;
        }
    }

    private void SetReturnRoute(int targetSpecimenId){
        switch (targetSpecimenId){

            case AprilTagConstant.GPP:
                ReturnFirstRowToLaunch();
                break;

            case AprilTagConstant.PGP:
                ReturnSecondRowToLaunch();
                break;

            case AprilTagConstant.PPG:
                ReturnThirdRowToLaunch();
                break;
        }
    }

    public DriveState GetState(){

        switch (_currentDriveState){

            case Scan:
                _camera.Update();

                int targetBasket = _camera.GetAprilTag();

                if (targetBasket == -1){
                    _telemetry.addData("Target Found!", targetBasket);
                    ChangeState(DriveState.DecdingNextSpeciment);
                }
                break;

            case DecdingNextSpeciment:
                _targetSpecimenId = IdentifyNextSpecimen();
                SetRowDestination(_targetSpecimenId);
                ChangeState(DriveState.DriveToRow);
                break;

            case DriveToRow:
                if(!_follower.isBusy()){
                    ChangeState(DriveState.WaitForLoadTrigger);
                }
                break;

            case WaitForLoadTrigger:

                if (_loadTriggerPulled){
                    SetIntakeRoute(_targetSpecimenId);
                    ChangeState(DriveState.LoadingSpecimens);
                    _loadTriggerPulled = false;
                }
                break;

            case LoadingSpecimens:
                if (!_follower.isBusy()){
                    ChangeState(DriveState.LoadingComplete);
                }
                break;

            case LoadingComplete:

                if (_returnTriggerPulled){
                    SetReturnRoute(_targetSpecimenId);
                    ChangeState(DriveState.DriveToLaunch);

                    _returnTriggerPulled = false;
                }
                break;

            case DriveToLaunch:
                if (!_follower.isBusy()){
                    ChangeState(DriveState.ReadyToFire);
                }
                break;
        }

        return _currentDriveState;
    }
}