package genealogy;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Output the class hierarchy or ancestry of a class.
 *
 * @author hom
 */
public class Genealogy {

    int c = new ArrayList<Class>().stream().mapToInt(p -> 1).sum();

    /**
     * @param args the command line arguments.
     */
    public static void main(String[] args) throws ClassNotFoundException {
        if (args.length == 0) {
            System.out.println("genealogy.Genealogy classname"
                    + " [[classname]...]\n"
                    + "as in genealogy.Genealogy java.lang.String"
            );
        }

        Genealogy gen = new Genealogy();
        for (String arg : args) {
            String sb = gen.getAncestry(arg);
            System.out.format("class hierarchy of [%s]%n", arg);
            System.out.println(sb);
        }
    }

    /**
     * Get the ancestry of the class or interface with the given name.
     * class hierarchy of [java.lang.StringBuilder]
     * public java.lang.Object
     * abstract java.lang.AbstractStringBuilder  implements public java.lang.Appendable, public java.lang.CharSequence
     * public final java.lang.StringBuilder  implements public java.io.Serializable, public java.lang.Comparable
     * //declared fields:
     * {
     * byte[] value  // declared in: AbstractStringBuilder
     * , byte coder  // declared in: AbstractStringBuilder
     * , int count  // declared in: AbstractStringBuilder
     * }
     *
     * @param typeName to use
     * @return a string containing the type hierarchy of the type
     */
    public String getAncestry(String typeName) {
        List<String> output = new ArrayList<String>();
        try {
            var classObj = Class.forName(typeName);
            var interfaces = classObj.getInterfaces();

            for (Class aClass : interfaces) {
                output.add(aClass.getName());
            }
            output.add(classObj.getName());

            while (!Objects.isNull(classObj)) {
                classObj = classObj.getSuperclass();
                if (!Objects.isNull(classObj)) {
                    var inters = classObj.getInterfaces();
                    List<String> intersOut = new ArrayList<>();
                    for (int i = 0; i < inters.length; i++) intersOut.add(inters[i].getName());
                    Collections.reverse(intersOut);
                    output.addAll(intersOut);
                    output.add(classObj.getName());
                }
            }

            Collections.reverse(output);

        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
        }


//        StringBuilder sb = new StringBuilder();
//        sb.append("class hierarchy of [").append(typeName).append("]\n");
//        Set<Class> superClasses = new HashSet<>();
//        superClasses.add(classObj);
//        for (int i = 0; i < 10; i++) {
//            superClasses.add(classObj.getSuperclass());
//        }
//        for(Class aClass: superClasses){
//
//            sb.append(aClass.getModifiers()).append(" ").append(aClass.getName()).append("\t");
//
//            Class[] interfaces = aClass.getInterfaces();
//            if(interfaces.length>0){
//                sb.append("implements ");
//                for(Class anInterface: interfaces){
//                    sb.append(anInterface.getName()).append(", ");
//                }
//            }
//            sb.append("\n");
//            sb.append("// declared fields:\n");
//            Field[] declaredFields = aClass.getDeclaredFields();
//            for(Field aField: declaredFields){
//                sb.append("\t").append(aField.getModifiers()).append(" ").append(aField.getType().getName()).append(" ").append(aField.getName()).append("\n");
//            }
//        }
//        return sb.toString();

        return output.toString();
    }

}
