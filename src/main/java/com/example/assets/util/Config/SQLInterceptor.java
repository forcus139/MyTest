//package com.example.assets.util.Config;
//
///**
// * TODO
// *
// * @author GengTianMing
// * @since 2023/03/09 09:05
// **/
//
//import com.alibaba.druid.sql.ast.SQLExpr;
//import com.alibaba.druid.sql.ast.SQLStatement;
//import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
//import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
//import com.alibaba.druid.sql.ast.statement.SQLSelect;
//import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
//import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
//import com.alibaba.druid.sql.parser.SQLExprParser;
//import com.alibaba.druid.sql.parser.SQLParserUtils;
//import com.alibaba.druid.sql.parser.SQLStatementParser;
//import com.alibaba.druid.util.JdbcUtils;
//import com.baomidou.mybatisplus.annotation.DbType;
//import org.apache.ibatis.executor.statement.StatementHandler;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.plugin.Intercepts;
//import org.apache.ibatis.plugin.Signature;
//import org.apache.ibatis.reflection.DefaultReflectorFactory;
//import org.apache.ibatis.reflection.MetaObject;
//import org.apache.ibatis.reflection.SystemMetaObject;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * MyBatis 允许你在映射语句执行过程中的某一点进行拦截调用。默认情况下，MyBatis 允许使用插件来拦截的方法调用包括：
// * <p>
// * Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
// * ParameterHandler (getParameterObject, setParameters)
// * ResultSetHandler (handleResultSets, handleOutputParameters)
// * StatementHandler (prepare, parameterize, batch, update, query)
// * 这些类中方法的细节可以通过查看每个方法的签名来发现，或者直接查看 MyBatis 发行包中的源代码。 如果你想做的不仅仅是监控方法的调用，那么你最好相当了解要重写的方法的行为。 因为在试图修改或重写已有方法的行为时，很可能会破坏 MyBatis 的核心模块。 这些都是更底层的类和方法，所以使用插件的时候要特别当心。
// * <p>
// * 通过 MyBatis 提供的强大机制，使用插件是非常简单的，只需实现 Interceptor 接口，并指定想要拦截的方法签名即可
// */
////@Intercepts({@Signature(
////        type= Executor.class,
////        method = "query",
////        args = {StatementHandler.class, Object.class, RowBounds.class, ResultHandler.class})})
//@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
//public class SQLInterceptor implements Interceptor {
//
//    private Properties properties = new Properties();
//
//    public List<String> getInsertPropertiesName(List<Map<String, Object>> properties) {
//        List<String> list = new ArrayList<>();
//        properties.forEach(data -> data.keySet()
//                .stream()
//                .filter(e -> e.equals("columnName"))
//                .forEach(e -> list.add((String) data.get("columnName"))));
//        return list;
//    }
//
//    public void setInsertProperties(List<Map<String, Object>> properties, String columnName, Object value) {
//        properties.forEach(element -> element.forEach((k, v) -> {
//            if (k.equals("columnName") && element.get(k).equals(columnName)) {
//                element.put("columnValue", value);
//                element.put("columnName", columnName);
//            }
//        }));
//    }
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        // implement pre processing if need
//        List<Map<String, Object>> insertProperties = new ArrayList<>();
//        Map<String, Object> nameProperties = new HashMap<>();
//        nameProperties.put("columnName", "name");
//        nameProperties.put("columnValue", "baby");
//        nameProperties.put("expr", "and");
//        insertProperties.add(nameProperties);
//
//        Map<String, Object> namePropertiesOr = new HashMap<>();
//        namePropertiesOr.put("columnName", "id");
//        namePropertiesOr.put("columnValue", "1");
//        namePropertiesOr.put("expr", "or");
//        insertProperties.add(namePropertiesOr);
//
//        // BoundSql boundSql = ((MappedStatement)invocation.getArgs()[0]).getBoundSql(invocation.getArgs()[1]);
//
//        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
//
//        // 获取传入的参数
//        Object parameterMappings = statementHandler.getBoundSql().getParameterObject();
//        if (parameterMappings instanceof Map) {
//            Map parameterMappingsConvert = (Map) parameterMappings;
//            getInsertPropertiesName(insertProperties).forEach(e -> {
//                Object value = parameterMappingsConvert.get(e);
//                if (value != null) { // 如果传参值不为空就覆盖配置值
//                    setInsertProperties(insertProperties, e, value);
//                }
//            });
//        }
//
//        MetaObject metaObject = MetaObject
//                .forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,
//                        new DefaultReflectorFactory());
//        BoundSql boundSql = statementHandler.getBoundSql();
//        SQLStatementParser sqlParser = SQLParserUtils.createSQLStatementParser(boundSql.getSql(), DbType.MYSQL.getValue());
//        SQLStatement stmt = sqlParser.parseStatementList().get(0);
//
//
//        System.out.println("Origin SQL is:" + boundSql.getSql());
//        System.out.println("++++++++++++++++++++++");
//
//        if (stmt instanceof SQLSelectStatement) {
//            // ((SQLSelectStatement) stmt).addWhere(sqlExpr);
//            // convert conditions to 'and' statement
//            StringBuilder constraintsBuffer = new StringBuilder();
//
//            boolean first = true;
//            for (Map<String, Object> data : insertProperties) {
//                if (String.valueOf(data.get("expr")).equalsIgnoreCase("and")) {
//                    if (!first) {
//                        constraintsBuffer.append(" AND ");
//                    }
//                    constraintsBuffer.append(String.format(" %s = '%s' ", data.get("columnName"), data.get("columnValue")));
//                } else if (String.valueOf(data.get("expr")).equalsIgnoreCase("or")) {
//                    if (!first) {
//                        constraintsBuffer.append(" OR ");
//                    }
//                    constraintsBuffer.append(String.format(" %s = '%s' ", data.get("columnName"), data.get("columnValue")));
//                }
//                first = false;
//            }
//
//            SQLExprParser constraintsParser = SQLParserUtils.createExprParser(constraintsBuffer.toString(), JdbcUtils.MYSQL);
//            SQLExpr constraintsExpr = constraintsParser.expr();
//
//            SQLSelectStatement selectStmt = (SQLSelectStatement) stmt;
//            // 拿到SQLSelect 通过在这里打断点看对象我们可以看出这是一个树的结构
//            SQLSelect sqlselect = selectStmt.getSelect();
//            SQLSelectQueryBlock query = (SQLSelectQueryBlock) sqlselect.getQuery();
//            SQLExpr whereExpr = query.getWhere();
//
//            // 修改where表达式
//            if (whereExpr == null) {
//                query.setWhere(constraintsExpr);
//            } else {
//                SQLBinaryOpExpr newWhereExpr = new SQLBinaryOpExpr(
//                        whereExpr, SQLBinaryOperator.BooleanAnd, constraintsExpr);
//                query.setWhere(newWhereExpr);
//            }
//
//            sqlselect.setQuery(query);
//            String sql = sqlselect.toString();
//
//            //通过反射修改sql语句
//            Field field = boundSql.getClass().getDeclaredField("sql");
//            field.setAccessible(true);
//            field.set(boundSql, sql);
//            System.out.println("modify sql is:" + sql);
//        }
//        // implement post processing if need
//        return invocation.proceed();
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        return Interceptor.super.plugin(target);
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//        this.properties = properties;
//    }
//}
//
