package org.rms.displaywrappers;


import org.apache.commons.beanutils.BeanUtils;
import org.rms.helpers.GridRow;
import org.rms.models.ParentNode;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cufa-01 on 6/2/15.
 */
public class ParentWrapper implements GridRow {

    private String[] VALID_BEAN_PROPERTIES = {"id", "firstName", "email"};

    private ParentNode parentNode;
    @Override
    public Long getId() {
        return parentNode.getId();
    }

    public ParentWrapper(ParentNode parentNode) {
        this.parentNode = parentNode;
    }

    @Override
    public List<String> getGridRow(Integer type) {
        List<String> convertedResult = new ArrayList<String>();
        try {
            for (int i = 0; i < VALID_BEAN_PROPERTIES.length; i++) {
                convertedResult.add(BeanUtils.getProperty(this.parentNode, VALID_BEAN_PROPERTIES[i]).toString());
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
