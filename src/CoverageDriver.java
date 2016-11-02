import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;

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

    private static final Map<String, FinalInfo> finalInfo = new HashMap<>();

    public static void collect(String className, String methodName, String mDesc, int line) {
        finalInfo.get(className).scnd.saveMethodInfo(methodName, mDesc, line);
    }

    public static void printResult() {
        for (Map.Entry<String, FinalInfo> entry : finalInfo.entrySet()) {
            System.out.println("class: " + entry.getKey());
            for (Map.Entry<FirstPassInfo.MethodsInfo, SortedSet<Integer>> entry1 : entry.getValue().first.map.entrySet()) {
                System.out.println("method: " + entry1.getKey());
                System.out.println("all statements: " + entry1.getValue());
                System.out.println("coveraged statements: " + entry.getValue().scnd.map.get(entry1.getKey()));
            }
            System.out.println();
        }
    }


    private final String projectName;


    public CoverageDriver(String pojectName) {
        this.projectName = pojectName;
    }

    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {

        if (className.contains(projectName) && !className.contains("/test/") && !className.contains("Test")) {

            FirstPassInfo info = new FirstPassInfo(className);
            byte[] ret = passOne(classfileBuffer, info, className);

            finalInfo.put(className, new FinalInfo(info, new SecondPassInfo(className)));

            ret = passTwo(ret, className);

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


