package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.qualcomm.robotcore.hardware.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Drive_Base_With_Intake_Test_Abhinav", group="Linear OpMode")
public class DriveBaseWithIntakeTestAbhinav extends LinearOpMode {

    private MecanumDrive drive;
    private CRServo sparkArm;

    @Override
    public void runOpMode() {

        sparkArm = hardwareMap.get(CRServo.class, "sparkArm");

        Motor frontLeft = new Motor(hardwareMap, "FrontLeft");
        Motor frontRight = new Motor(hardwareMap, "FrontRight");
        Motor backLeft = new Motor(hardwareMap, "BackLeft");
        Motor backRight = new Motor(hardwareMap, "BackRight");

        frontLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        // ✅ Typical mecanum configuration:
        frontLeft.setInverted(true);
        frontRight.setInverted(true);
        backLeft.setInverted(true);
        backRight.setInverted(true);

        drive = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);

        waitForStart();

        int power;

        while (opModeIsActive()) {
            drive.driveRobotCentric(
                    gamepad1.left_stick_x,   // strafe left/right
                    -gamepad1.left_stick_y,   // forward/back
                    gamepad1.right_stick_x    // rotation
            );

            power = (int)(gamepad1.right_trigger);

            sparkArm.setPower(power);

            telemetry.addData("SPARKmini Power", power);
            telemetry.update();
        }
    }
}
