package org.firstinspires.ftc.teamcode.paladins.Tasks;

import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.teamcode.paladins.common.PaladinsOpMode;
import org.firstinspires.ftc.teamcode.paladins.common.TankDrive;

public class ATaskThatDrivesALeftAndRightMotorAndDoesntCareWhatConfigItIs extends BaseTask implements Task {

    private final TankDrive drive;
    private final double leftSpeed;
    private final double rightSpeed;
    private final ColorSensor colourSensor;

    public ATaskThatDrivesALeftAndRightMotorAndDoesntCareWhatConfigItIs(PaladinsOpMode opMode, double time, TankDrive drive, double leftSpeed, double rightSpeed, ColorSensor colorSensor) {
        super(opMode, time);
        this.drive = drive;
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
        this.colourSensor = colorSensor;
    }

    @Override
    public void init() {
        super.init();
        drive.setEncoderMode(false);
    }

    @Override
    public void run() {
        if (isFinished()) {
            drive.setPower(0,0);
            drive.update();
            return;
        }
        if (colourSensor.green() > 210) {
            this.isFinished=true;
            drive.setPower(0,0);
            drive.update();
            return;
        }
        drive.setPower(leftSpeed, rightSpeed);
        drive.update();
    }

}
