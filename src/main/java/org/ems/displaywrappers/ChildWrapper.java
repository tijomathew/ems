package org.rms.displaywrappers;

;
import org.apache.commons.beanutils.BeanUtils;
import org.rms.helpers.GridRow;
import org.rms.models.StudentNode;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cufa-01 on 6/2/15.
 */
public class ChildWrapper implements GridRow {

    private String[] VALID_BEAN_PROPERTIES = {"id", "firstName", "lastName"};

    private StudentNode studentNode;
    @Override
    public Long getId() {
        return studentNode.getId();
    }

    public ChildWrapper(StudentNode studentNode) {
        this.studentNode = studentNode;
    }

    @Override
    public List<String> getGridRow(Integer type) {
        List<String> convertedResult = new ArrayList<String>();
        try {
            for (int i = 0; i < VALID_BEAN_PROPERTIES.length; i++) {
                convertedResult.add(BeanUtils.getProperty(this.studentNode, VALID_BEAN_PROPERTIES[i]).toString());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return convertedResult;
    }
}
