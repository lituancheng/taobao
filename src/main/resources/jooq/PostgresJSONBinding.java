package jooq;

import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.Objects;

/**
 * @author Gink
 * @create 2018/1/2 下午5:54
 */
public class PostgresJSONBinding implements Binding<Object, String> {

    // The converter does all the work
    @Override
    public Converter<Object, String> converter() {
        return new Converter<Object, String>() {
            @Override
            public String from(Object t) {
                return (String) t;
            }

            @Override
            public Object to(String u) {
                return u;
            }

            @Override
            public Class<Object> fromType() {
                return Object.class;
            }

            @Override
            public Class<String> toType() {
                return String.class;
            }
        };
    }

    // Rending a bind variable for the binding context's value and casting it to the json type
    @Override
    public void sql(BindingSQLContext<String> ctx) {
        // Depending on how you generate your SQL, you may need to explicitly distinguish
        // between jOOQ generating bind variables or inlined literals. If so, use this check:
        // ctx.render().paramType() == INLINED
        ctx.render().visit(DSL.val(ctx.convert(converter()).value())).sql("");
    }

    // Registering VARCHAR types for JDBC CallableStatement OUT parameters
    @Override
    public void register(BindingRegisterContext<String> ctx) throws SQLException {
        ctx.statement().registerOutParameter(ctx.index(), Types.VARCHAR);
    }

    // Converting the String to a String value and setting that on a JDBC PreparedStatement
    @Override
    public void set(BindingSetStatementContext<String> ctx) throws SQLException {
        ctx.statement().setString(ctx.index(), Objects.toString(ctx.convert(converter()).value(), null));
    }

    // Getting a String value from a JDBC ResultSet and converting that to a String
    @Override
    public void get(BindingGetResultSetContext<String> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.resultSet().getString(ctx.index()));
    }

    // Getting a String value from a JDBC CallableStatement and converting that to a String
    @Override
    public void get(BindingGetStatementContext<String> ctx) throws SQLException {
        ctx.convert(converter()).value(ctx.statement().getString(ctx.index()));
    }

    // Setting a value on a JDBC SQLOutput (useful for Oracle OBJECT types)
    @Override
    public void set(BindingSetSQLOutputContext<String> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    // Getting a value from a JDBC SQLInput (useful for Oracle OBJECT types)
    @Override
    public void get(BindingGetSQLInputContext<String> ctx) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}