package com.example.tabletoprpgmaker;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;

import java.util.Locale;

public class ScriptEvaluator {

    /**
     * Evaluates the given JavaScript script using the GraalVM context. Binds properties to the script,
     * executes it, and updates the dependent property based on the script result.
     *
     * @param scriptText the JavaScript code to be evaluated
     * @param dependentProp the property to be updated based on the script result
     * @param otherProps the properties to be used in the script
     */
    public void evaluateScript(String scriptText, ComponentProperty dependentProp, ComponentProperty... otherProps) {
        scriptText = scriptText.toLowerCase();
        try (Context context = Context.newBuilder("js")
                .allowHostAccess(HostAccess.NONE) // Restrict access to host classes
                .allowIO(false) // Disallow IO operations
                .option("engine.WarnInterpreterOnly", "false")
                .build()) {

            bindProp(context, dependentProp);
            for (ComponentProperty prop : otherProps) {
                bindProp(context, prop);
            }

            System.out.println(scriptText);
            System.out.println(dependentProp.getStringVal());
            System.out.println(otherProps[0].getStringVal());

            Value result = context.eval("js", scriptText);

            if (result != null) {
                if (dependentProp instanceof NumberProperty)
                    if (result.isNumber()) {
                        dependentProp.setVal(result.asDouble());
                    } else {
                        //System.out.println("Invalid script result: " + result.);
                    }
                else if (dependentProp instanceof ComplexStringProperty)
                    dependentProp.setVal(result.asString());
                else if (dependentProp instanceof ComplexBooleanProperty)
                    if (result.isBoolean()) {
                        dependentProp.setVal(result.asBoolean());
                    } else {
                        //System.out.println("Invalid script result: " + result);
                    }
            }
            else {
                System.out.println("No result defined");
            }
            /**/
        } catch (Exception e) {
            System.out.println("Invalid script: " + e.getMessage());
        }
    }

    /**
     * Binds a ComponentProperty to the JavaScript context.
     * The property is converted to its appropriate value type and made available in the script context.
     *
     * @param context the GraalVM context for script execution
     * @param prop the ComponentProperty to be bound to the context
     */
    private void bindProp(Context context, ComponentProperty prop) {
        Object value = Component.parseValue(prop.getStringVal());
        context.getBindings("js").putMember(prop.getName().toLowerCase(), value);
    }

}
