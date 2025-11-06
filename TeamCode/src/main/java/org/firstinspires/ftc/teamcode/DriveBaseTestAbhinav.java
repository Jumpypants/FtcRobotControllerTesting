package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="DRIVE_BASE_TEST_ABHINAV", group="Linear OpMode")
public class DriveBaseTestAbhinav extends LinearOpMode {

    private MecanumDrive drive;

    @Override
    public void runOpMode() {
        // --- Initialize 4 drive motors ---
        Motor frontLeft = new Motor(hardwareMap, "FrontLeft");
        Motor frontRight = new Motor(hardwareMap, "FrontRight");
        Motor backLeft = new Motor(hardwareMap, "BackLeft");
        Motor backRight = new Motor(hardwareMap, "BackRight");

        // Reverse one side for correct drive direction
        frontRight.setInverted(true);
        frontLeft.setInverted(true);
        backRight.setInverted(true);
        backLeft.setInverted(true);


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