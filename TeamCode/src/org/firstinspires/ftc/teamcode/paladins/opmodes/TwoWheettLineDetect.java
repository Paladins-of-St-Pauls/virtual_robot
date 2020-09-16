package org.firstinspires.ftc.teamcode.paladins.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;


import org.firstinspires.ftc.teamcode.paladins.Tasks.ATaskThatDrivesALeftAndRightMotorAndDoesntCareWhatConfigItIs;
import org.firstinspires.ftc.teamcode.paladins.Tasks.TankDriveEncTask;
import org.firstinspires.ftc.teamcode.paladins.Tasks.TankDriveTask;
import org.firstinspires.ftc.teamcode.paladins.Tasks.Task;
import org.firstinspires.ftc.teamcode.paladins.common.PaladinsOpMode;
import org.firstinspires.ftc.teamcode.paladins.common.TankDrive;
import org.firstinspires.ftc.teamcode.paladins.config.TwoWheelBotConfiguration;

import java.util.ArrayDeque;

@Autonomous(name = "Two Wheet Line detect")
public class TwoWheettLineDetect extends PaladinsOpMode {
    private TwoWheelBotConfiguration config;
    private TankDrive drive;
    private ArrayDeque<Task> tasks = new ArrayDeque<>();

    @Override
    protected void onInit() {
        config = TwoWheelBotConfiguration.newConfig(hardwareMap, telemetry);

        drive = new TankDrive(this, gamepad1, config.leftMotor, config.rightMotor);
        drive.setCountsPerCm(100.0);
        config.leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        config.rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        tasks.add(new ATaskThatDrivesALeftAndRightMotorAndDoesntCareWhatConfigItIs(this, 15.0, drive, 0.4, 0.4, config.colorSensor));
    }

    @Override
    protected void activeLoop() throws InterruptedException {

        telemetry.addData("Num Tasks", tasks.size());
        telemetry.addData("loop count", loopCount);
        Task currentTask = tasks.peekFirst();
        if (currentTask == null) {
            return;
        }
        currentTask.run();
        if (currentTask.isFinished()){
            tasks.removeFirst();

        }
        if (tasks.isEmpty()) {
            drive.setPower(0, 0);
            drive.update();
        }
    }
}