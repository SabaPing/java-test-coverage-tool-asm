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
     */

    private String pojectName;

    public CoverageDriver(String pojectName) {
        this.pojectName = pojectName;
    }

    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {

        FirstPassInfo info = new FirstPassInfo();

        byte[] ret = passOne(classfileBuffer, info);

        if (info.needAdapt) System.out.println(info);
//
//        if (info.needAdapt) return passTwo(ret);
//        else return ret;
        return ret;
    }

    private byte[] passOne(byte[] classByte, FirstPassInfo info) {
        byte[] ret;
        ClassReader cr = new ClassReader(classByte);
        ClassWriter cw = new ClassWriter(cr, 0);
        InformationCollecter ca = new InformationCollecter(cw, pojectName);
        cr.accept(ca, 0);
        info.statementsCount = ca.getStatementsCounter();
        info.needAdapt = ca.getNeedAdapt();
        info.name = ca.getName();
        ret = cw.toByteArray();
        return ret;
    }

    private byte[] passTwo(byte[] classByte) {
        byte[] ret;
        ClassReader cr = new ClassReader(classByte);
        ClassWriter cw = new ClassWriter(cr, 0);
        CoverageAdapter ca = new CoverageAdapter(cw);
        cr.accept(ca, 0);
        ret = cw.toByteArray();
        return ret;
    }

    private class FirstPassInfo {
        int statementsCount;
        boolean needAdapt;
        String name;

        @Override
        public String toString() {
            return "Class " + name + " has " + statementsCount + " statements.";
        }
    }
}
