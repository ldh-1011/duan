package udpm.hn.server.test.infrastructure.cloudinary;

import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.FunctorException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class FileUpLoad {
    private static final long MAX_UPLOAD_SIZE = 1024 * 1024 * 2;

    public static final String IMAGES_PATTERN = ".*\\.(png|jpg|jpeg|webp|gif)$";

    public static final String DATE_FORMAT= "yyyyMMdd_HHmmss";

    public static final String FILE_NAME_FORMAT = "%s-%s";

    public static boolean isAllowedExtension(final String fileName , final String pattern) {
        final Matcher matcher = Pattern.compile(pattern , Pattern.CASE_INSENSITIVE).matcher(fileName);
        return matcher.matches();
    }

    public static void assertAllowed(MultipartFile file , String pattern) {
        final long size = file.getSize();
        if (size > MAX_UPLOAD_SIZE) {
            throw new FunctorException("Max file size is 2MB");
        }

        final String fileName = file.getOriginalFilename();
        final String extension = FilenameUtils.getExtension(fileName);
        if (!isAllowedExtension(fileName, pattern)) {
            throw new IllegalArgumentException("Only png , jpg , jpeg , webp , gif files are allowed");
        }
    }

     public static String getFileName(final String name ) {
        final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        final String date = dateFormat.format(System.currentTimeMillis());
        return String.format(FILE_NAME_FORMAT , name , date);
     }
}
