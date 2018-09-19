/*
 * This file is generated by jOOQ.
*/
package com.leon.taobao.model.taobao;


import com.leon.taobao.model.taobao.tables.Message;
import com.leon.taobao.model.taobao.tables.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.2"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DefaultSchema extends SchemaImpl {

    private static final long serialVersionUID = 1577610928;

    /**
     * The reference instance of <code></code>
     */
    public static final DefaultSchema DEFAULT_SCHEMA = new DefaultSchema();

    /**
     * The table <code>message</code>.
     */
    public final Message MESSAGE = com.leon.taobao.model.taobao.tables.Message.MESSAGE;

    /**
     * The table <code>user</code>.
     */
    public final User USER = com.leon.taobao.model.taobao.tables.User.USER;

    /**
     * No further instances allowed
     */
    private DefaultSchema() {
        super("", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Message.MESSAGE,
            User.USER);
    }
}