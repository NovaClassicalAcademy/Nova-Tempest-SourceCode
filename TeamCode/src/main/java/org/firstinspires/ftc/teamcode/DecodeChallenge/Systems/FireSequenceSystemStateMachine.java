package org.firstinspires.ftc.teamcode.DecodeChallenge.Systems;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.DistanceSensorController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.IntakeController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.LaunchController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.ScooperController;

public class FireSequenceSystemStateMachine {
    public enum LaunchState {Complete, Initialize, BallSense, SpinningUp, Fire, Reset }
    public enum LaunchDistance {Far, Close}
    private LaunchState _currentState = LaunchState.Complete;
    private final Telemetry _telemetry;
    private final LaunchController _launcher;
    private final ScooperController _scooper;
    private final IntakeController _intake;
    private final DistanceSensorController _distanceSensor;
    private final ElapsedTime _stateTimer = new ElapsedTime();

    public  int _shotsRemaining = 0;

    private final double FAR_LAUNCH_VELOCITY = 5100;
    private final double CLOSE_LAUNCH_VELOCITY = 2000;

    private final double BALL_PRESENT_DISTANCE = 4;
    private final double SCOOP_UP_TIME = 0.8;
    private final double SCOOP_DOWN_TIME = 0.8;
    private final double MAX_TIME_OUT = 1.0;

    public FireSequenceSystemStateMachine(Telemetry telemetry, RobotMapping rc) {
        _telemetry = telemetry;
        _intake = new IntakeController(rc.UpperLeftIntake, rc.UpperRightIntake, rc.LowerLeftIntake, rc.LowerRightIntake);
        _launcher = new LaunchController(rc.Goat);
        _scooper = new ScooperController(rc.Scooper);
        _distanceSensor = new DistanceSensorController(rc.RearColorSensor);
    }

    public void InitFarFireMode(){
        _launcher.StartVelocity(FAR_LAUNCH_VELOCITY);
        ChangeState(LaunchState.Initialize);
    }

    public void InitializeClose(){
        _launcher.StartVelocity(CLOSE_LAUNCH_VELOCITY);
        ChangeState(LaunchState.Initialize);
    }

    public boolean IsComplete(){
        return _currentState == LaunchState.Complete;
    }

    public void ProcessFireMode() {
        _telemetry.addData("Fire State: ", _currentState);
        _distanceSensor.DebugOutuput(_telemetry);
        _launcher.ReportVelocity(_telemetry);

        switch (_currentState) {
            case Initialize:
                _shotsRemaining = 3;
                _scooper.ScoopDown();

                if (_shotsRemaining > 0 || _stateTimer.seconds() > MAX_TIME_OUT) {
                    ChangeState(LaunchState.BallSense);
                }
                break;

            case BallSense:
                if (_distanceSensor.GetDistanceInches() < BALL_PRESENT_DISTANCE || _stateTimer.seconds() > MAX_TIME_OUT) {
                    _telemetry.addLine("Ball is Seen");
                    ChangeState(LaunchState.SpinningUp);
                }
                break;

            case SpinningUp:
                if (_launcher.IsAtFullSpeed() || _stateTimer.seconds() > MAX_TIME_OUT) {
                    _launcher.ReportVelocity(_telemetry);
                    ChangeState(LaunchState.Fire);
                }
                break;

            case Fire:
                _scooper.ScoopUp();
                _intake.Activate();

                if (_stateTimer.seconds() > SCOOP_UP_TIME) {
                    _telemetry.addLine("Shot a Ball");
                    _shotsRemaining--;
                    _intake.Deactivate();

                    ChangeState(LaunchState.Reset);
                }
                break;

            case Reset:
                _scooper.ScoopDown();

                if (_stateTimer.seconds() > SCOOP_DOWN_TIME){
                    if (_shotsRemaining > 0){
                        _telemetry.addData("Shots Left: ", _shotsRemaining);
                        ChangeState(LaunchState.BallSense);
                    } else {
                        _launcher.Stop();
                        _telemetry.addLine("Finished");
                        ChangeState(LaunchState.Complete);
                    }
                }
                break;
        }
    }

    private void ChangeState(LaunchState newState){
        _currentState = newState;
        _stateTimer.reset();
    }
}
