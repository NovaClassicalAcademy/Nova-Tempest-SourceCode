package org.firstinspires.ftc.teamcode.DecodeChallenge.Systems;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.DecodeChallenge.AllianceColor;
import org.firstinspires.ftc.teamcode.DecodeChallenge.AprilTagConstant;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.CameraController;

import java.util.ArrayList;
import java.util.List;

public class DecodeDriveSystemStateMachine {

    public enum DriveState { DecdingNextSpeciment, DriveToRow, WaitForLoadTrigger, LoadingSpecimens, LoadingComplete, DriveToLaunch, ReadyToFire, Idle }

    private final Telemetry _telemetry;
    private final Follower _follower;
    private final CameraController _camera;

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

    private final AllianceColor _allianceColor;

    private List<Integer> _completedSpecimens = new ArrayList<>();
    private int _targetSpecimenId;
    private int _startingSpecimen;
    private DriveState _currentDriveState;
    private boolean _loadTriggerPulled;
    private boolean _returnTriggerPulled;

    public DecodeDriveSystemStateMachine(Telemetry telemetry, Follower follower, RobotMapping rm, AllianceColor allianceColor) {
        _telemetry = telemetry;
        _follower = follower;
        _allianceColor = allianceColor;
        _camera = new CameraController(telemetry, rm.Webcam, allianceColor);

        _loadTriggerPulled = false;
        _returnTriggerPulled = false;
    }

    public void Init(){

        int startingSpecimen = _camera.GetDecode();
        if (startingSpecimen != -1){
            _startingSpecimen = startingSpecimen;
        }
        else{
            _startingSpecimen = AprilTagConstant.GPP;
        }

        // TODO: MAKE THE DEFAULT START POSTION IS EITHER RED OR BLUE SIDE
        Pose startPosition = null;
        switch (_allianceColor)
        {
            case Blue:
                // TODO: WHAT IS THE BLUE SIDE START POINT?
                startPosition = new Pose(56.000, 8.000, Math.toRadians(90));
                break;

            case Red:
                // TODO: WHAT IS THE RED SIDE START POINT?
                startPosition = new Pose(56.000, 8.000, Math.toRadians(90));
                break;
        }

        Pose cameraPosition = _camera.GetRobotPose();

        if (cameraPosition != null) {
            _follower.setStartingPose(cameraPosition);
        }
        else {
            _follower.setStartingPose(startPosition);
        }

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

    // TODO: THIS WILL HAVE TO CHANGE BASED ON TEAM COLOR
    // WE COULD ABSTRACT THIS BUILD PATHS INTO SEPARATE CLASSES
    // SO WE CAN HAVE ONE FOR A BLUE AND RED AND PASS IN REFERENCE,
    // OR DO A SWITCH CASE THAT HAS BOTH COLOR PATHS IN HERE
    private void BuildPaths(){

        _pathAlignToFirstRow = _follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(56.000, 8.000),
                                new Pose(62.814, 32.989),
                                new Pose(40.432, 35.385)))
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                .build();

        _pathIntakeRow1 = _follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(40.432, 36.244),

                                new Pose(14.269, 36.660)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        _pathReturnFirstRowToLaunch = _follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(14.269, 36.660),

                                new Pose(55.995, 7.909)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        _pathAlignToSecondRow = _follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(55.995, 7.909),
                                new Pose(60.020, 59.855),
                                new Pose(40.939, 59.853)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        _pathIntakeRow2 = _follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(40.939, 59.853),

                                new Pose(13.015, 60.431)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        _pathReturnSecondRowToLaunch = _follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(13.015, 60.431),

                                new Pose(55.970, 7.797)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        _pathAlignToThirdRow = _follower.pathBuilder().addPath(
                        new BezierCurve(
                                new Pose(55.970, 7.797),
                                new Pose(73.340, 84.183),
                                new Pose(43.391, 83.716)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        _pathIntakeRow3 = _follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(43.391, 83.716),

                                new Pose(11.914, 84.051)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        _pathReturnThirdRowToLaunch = _follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(11.914, 84.051),

                                new Pose(56.249, 7.934)
                        )
                ).setTangentHeadingInterpolation()

                .build();

        _moveOutOfLaunch = _follower.pathBuilder().addPath(
                        new BezierLine(
                                new Pose(56.249, 7.934),

                                new Pose(46.401, 31.477)
                        )
                ).setTangentHeadingInterpolation()

                .build();
    }

    private void AlignToFirstRow(int _targetSpecimenId){
        _follower.followPath(_pathAlignToFirstRow);
    }

    private void IntakeFirstRow(){
        _follower.followPath(_pathIntakeRow1);
    }

    private void ReturnFirstRowToLaunch(){
        _follower.followPath(_pathReturnFirstRowToLaunch);
    }

    private void AlignToSecondRow(){
        _follower.followPath(_pathAlignToSecondRow);
    }

    private void IntakeSecondRow() { _follower.followPath(_pathIntakeRow2); }

    private void ReturnSecondRowToLaunch(){
        _follower.followPath(_pathReturnSecondRowToLaunch);
    }

    private void AlignToThirdRow(){
        _follower.followPath(_pathAlignToThirdRow);
    }

    private void IntakeThirdRow() {
        _follower.followPath(_pathIntakeRow3);
    }

    private void ReturnThirdRowToLaunch(){
        _follower.followPath(_pathReturnThirdRowToLaunch);
    }

    private void MoveOutOfLaunch() {
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