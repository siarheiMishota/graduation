package by.mishota.graduation.validation;

import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

public class EnterCertificatesValidatorTest {

    @Test
    public void testValidateIfCorrectIdInSubjectStringInParameter() {
        assertTrue(EnterCertificatesValidator.validateMark("13").isEmpty());
    }

}