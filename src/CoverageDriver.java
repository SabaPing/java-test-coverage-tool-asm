import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * Created by Yifan on 10/28/16.
 */

public class CoverageDriver implements ClassFileTransformer {
    /**
     * This class must contain current transforming class's total # of statements.
     * todo 我们也how to be driven by junti start/end event?
     * IPC uses shared file.
     * This project has a assumption: a single line contains only one statement.
     */

    private final String projectName;


    public CoverageDriver(String pojectName) {
        this.projectName = pojectName;
    }

    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {

        if (className.contains(projectName) && !className.contains("/test/")) {

            FirstPassInfo info = new FirstPassInfo(className);
            byte[] ret = passOne(classfileBuffer, info, className);

            System.out.println(info);

//            ret = passTwo(ret, className);
            return ret;
        } else return classfileBuffer;
    }

    private byte[] passOne(byte[] classByte, FirstPassInfo info, String name) {
        byte[] ret;
        ClassReader cr = new ClassReader(classByte);
        ClassWriter cw = new ClassWriter(cr, 0);
        InformationCollecter ca = new InformationCollecter(cw, info, name);
        cr.accept(ca, 0);
        ret = cw.toByteArray();
        return ret;
    }

    private byte[] passTwo(byte[] classByte, String name) {
        byte[] ret;
        ClassReader cr = new ClassReader(classByte);
        ClassWriter cw = new ClassWriter(cr, 0);
        CoverageAdapter ca = new CoverageAdapter(cw, name);
        cr.accept(ca, 0);
        ret = cw.toByteArray();
        return ret;
    }


}


