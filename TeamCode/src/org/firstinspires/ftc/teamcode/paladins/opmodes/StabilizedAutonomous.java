package org.firstinspires.ftc.teamcode.paladins.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.paladins.Tasks.TankDriveEncTask;
import org.firstinspires.ftc.teamcode.paladins.Tasks.TankDriveTask;
import org.firstinspires.ftc.teamcode.paladins.Tasks.Task;
import org.firstinspires.ftc.teamcode.paladins.common.PaladinsOpMode;
import org.firstinspires.ftc.teamcode.paladins.common.TankDrive;
import org.firstinspires.ftc.teamcode.paladins.config.TwoWheelBotConfiguration;

import java.util.ArrayDeque;

@Autonomous(name = "Stabilized Autonomous")
public class StabilizedAutonomous extends PaladinsOpMode {
    private TwoWheelBotConfiguration config;
    private TankDrive drive;
    private ArrayDeque<Task> tasks = new ArrayDeque<>();

    @Override
    protected void onInit() {
        config = TwoWheelBotConfiguration.newConfig(hardwareMap, telemetry);

        drive = new TankDrive(this, gamepad1, config.leftMotor, config.rightMotor);
        tasks.add(new TankDriveTask(this, 0.5, drive, 1, 1));
        tasks.add(new TankDriveTask(this, 20, drive, 1, 1));
    }

    @Override
    protected void activeLoop() throws InterruptedException {
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