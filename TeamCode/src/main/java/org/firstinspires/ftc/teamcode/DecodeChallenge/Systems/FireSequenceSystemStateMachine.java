package org.firstinspires.ftc.teamcode.DecodeChallenge.Systems;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.DistanceSensorController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.IntakeController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.LaunchController;
import org.firstinspires.ftc.teamcode.DecodeChallenge.Controllers.ScooperController;

public class FireSequence {
    public enum LaunchState {Idle, BallSense, SpinningUp, Fire, Reset }
    private LaunchState _currentState = LaunchState.Idle;
    private final Telemetry _telemetry;
    private final LaunchController _launcher;
    private final ScooperController _scooper;
    private final IntakeController _intake;
    private final DistanceSensorController _distanceSensor;
    private final ElapsedTime _stateTimer = new ElapsedTime();

    public  int _shotsRemaining = 0;

    private final double BALL_PRESENT_DISTANCE = 2.0;
    private final double SCOOP_UP_TIME = 0.8;
    private final double SCOOP_DOWN_TIME = 0.8;
    private final double MAX_SPIN_UP_TIME = 1.5;

    public FireSequence(Telemetry telemetry, RobotMapping rc) {
        _telemetry = telemetry;
        _intake = new IntakeController(rc.UpperLeftIntake, rc.UpperRightIntake, rc.LowerLeftIntake, rc.LowerRightIntake);
        _launcher = new LaunchController(rc.Goat, 2600);
        _scooper = new ScooperController(rc.Scooper);
        _distanceSensor = new DistanceSensorController(rc.ColorSensor);
    }

    public void setLaunchVelocity (double velocity){
        _launcher.updateTargetVelocity(velocity);
    }

    public LaunchState GetStatus() {
        _telemetry.addData("Fire State: ", _currentState);
        _distanceSensor.DebugOutuput(_telemetry);
        _launcher.ReportVelocity(_telemetry);

        switch (_currentState) {
            case Idle:
                _scooper.ScoopDown();

                if (_shotsRemaining > 0) {
                    ChangeState(LaunchState.BallSense);
                }
                break;

            case BallSense:
                if (_distanceSensor.GetDistanceCm() < BALL_PRESENT_DISTANCE) {
                    _launcher.StartVelocity();
                    ChangeState(LaunchState.SpinningUp);
                }
                break;

            case SpinningUp:
                if (_launcher.IsAtFullSpeed() || _stateTimer.seconds() > MAX_SPIN_UP_TIME) {
                    ChangeState(LaunchState.Fire);
                }
                break;

            case Fire:
                _scooper.ScoopUp();
                _intake.Activate();

                if (_stateTimer.seconds() > SCOOP_UP_TIME) {
                    _shotsRemaining--;
                    _intake.Deactivate();
                    ChangeState(LaunchState.Reset);
                }
                break;

            case Reset:
                _scooper.ScoopDown();

                if (_stateTimer.seconds() > SCOOP_DOWN_TIME){
                    if (_shotsRemaining > 0){
                        ChangeState(LaunchState.BallSense);
                    } else {
                        _launcher.Stop();
                        _currentState = LaunchState.Idle;
                    }
                }
                break;
        }
        return _currentState;
    }

    private void ChangeState(LaunchState newState){
        _currentState = newState;
        _stateTimer.reset();
    }

    public void startSequence(int count){
        _shotsRemaining = count;
    }
}
