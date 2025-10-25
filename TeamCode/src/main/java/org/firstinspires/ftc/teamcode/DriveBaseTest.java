package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="DRIVE_BASE_TEST", group="Linear OpMode")
public class DriveBaseTest extends LinearOpMode {

    private MecanumDrive drive;

    @Override
    public void runOpMode() {
        // --- Initialize 4 drive motors ---
        Motor frontLeft = new Motor(hardwareMap, "frontLeft");
        Motor frontRight = new Motor(hardwareMap, "frontRight");
        Motor backLeft = new Motor(hardwareMap, "backLeft");
        Motor backRight = new Motor(hardwareMap, "backRight");

        // Reverse one side for correct drive direction
        frontRight.setInverted(true);
        backRight.setInverted(true);

        drive = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);

        waitForStart();

        while (opModeIsActive()) {
            drive.driveRobotCentric(
                    -gamepad1.left_stick_y,
                    gamepad1.left_stick_x,
                    gamepad1.right_stick_x
            );

        }
    }
}