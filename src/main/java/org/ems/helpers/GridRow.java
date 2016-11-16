package org.rms.helpers;

import java.util.List;

/**
 * Created by tijo on 1/12/14.
 */
public interface GridRow {

    final Integer LIST_STRING_TYPE = 0;

    Long getId();

    List<String> getGridRow(Integer type);
}
