//package com.example.assets.util.Config;
//
///**
// * TODO
// *
// * @author GengTianMing
// * @since 2023/03/09 08:59
// **/
//
//
//import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
//import lombok.extern.slf4j.Slf4j;
//import net.sf.jsqlparser.expression.Expression;
//import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
//import net.sf.jsqlparser.parser.CCJSqlParserManager;
//import net.sf.jsqlparser.parser.CCJSqlParserUtil;
//import net.sf.jsqlparser.schema.Column;
//import net.sf.jsqlparser.schema.Table;
//import net.sf.jsqlparser.statement.Statement;
//import net.sf.jsqlparser.statement.insert.Insert;
//import net.sf.jsqlparser.statement.select.FromItem;
//import net.sf.jsqlparser.statement.select.PlainSelect;
//import net.sf.jsqlparser.statement.select.Select;
//import net.sf.jsqlparser.statement.update.Update;
//import org.apache.ibatis.cache.CacheKey;
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.plugin.Intercepts;
//import org.apache.ibatis.plugin.Invocation;
//import org.apache.ibatis.plugin.Plugin;
//import org.apache.ibatis.plugin.Signature;
//import org.apache.ibatis.reflection.MetaObject;
//import org.apache.ibatis.reflection.SystemMetaObject;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.session.RowBounds;
//
//import java.io.StringReader;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//
//@Slf4j
//@Intercepts({
//        @Signature( type = Executor.class, method = "update",args = {MappedStatement.class, Object.class}),
//        @Signature(type = Executor.class, method = "query",args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
//        @Signature(type = Executor.class, method = "query",args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class,BoundSql.class})
//
//})
//public class AreaInterceptor implements Interceptor {
//
//    private static final String  COLUMN_NAME = "platform_id";
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//
//        String processSql = ExecutorPluginUtils.getSqlByInvocation(invocation);
//        log.debug("schema替换前：{}", processSql);
//        // 执行自定义修改sql操作
//        // 获取sql
//        String sql2Reset = processSql;
//        //忽略sql中包含on conflict的情况
//        Statement statement = CCJSqlParserUtil.parse(processSql);
//
//        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
//
//        if (ExecutorPluginUtils.isAreaTag(mappedStatement)) {
//            try {
//                if (statement instanceof Update) {
//                    Update updateStatement = (Update) statement;
//                    Table table = updateStatement.getTables().get(0);
//                    if (table != null) {
//                        List<Column> columns = updateStatement.getColumns();
//                        List<Expression> expressions = updateStatement.getExpressions();
//                        columns.add(new Column(COLUMN_NAME));
//                        //CurrentPlatformIdCache.getCurrentPlatformId()为COLUMN_NAME对应的值
//                        expressions.add(CCJSqlParserUtil.parseExpression(CurrentPlatformIdCache.getCurrentPlatformId()));
//
//                        updateStatement.setColumns(columns);
//                        updateStatement.setExpressions(expressions);
//
//                        sql2Reset = updateStatement.toString();
//                    }
//
//                }
//                if (statement instanceof Insert) {
//                    Insert insertStatement = (Insert) statement;
//                    List<Column> columns = insertStatement.getColumns();
//                    ExpressionList itemsList = (ExpressionList) insertStatement.getItemsList();
//                    columns.add(new Column(COLUMN_NAME));
//                    List<Expression> list = new ArrayList<>();
//                    list.addAll(itemsList.getExpressions());
//                    list.add(CCJSqlParserUtil.parseExpression(CurrentPlatformIdCache.getCurrentPlatformId()));
//                    itemsList.setExpressions(list);
//                    insertStatement.setItemsList(itemsList);
//                    insertStatement.setColumns(columns);
//                    sql2Reset = insertStatement.toString();
//                }
//                if (statement instanceof Select) {
//                    Select selectStatement = (Select) statement;
//                    PlainSelect plain = (PlainSelect) selectStatement.getSelectBody();
//                    FromItem fromItem = plain.getFromItem();
//                    //获取到原始sql语句
//                    String sql = processSql;
//                    StringBuffer whereSql = new StringBuffer();
//                    //增加sql语句的逻辑部分处理
//                    if (fromItem.getAlias() != null) {
//                        whereSql.append(fromItem.getAlias().getName()).append(".platform_id = ").append(CurrentPlatformIdCache.getCurrentPlatformId());
//                    } else {
//                        whereSql.append("platform_id = ").append(CurrentPlatformIdCache.getCurrentPlatformId());
//                    }
//                    Expression where = plain.getWhere();
//                    if (where == null) {
//                        if (whereSql.length() > 0) {
//                            Expression expression = CCJSqlParserUtil
//                                    .parseCondExpression(whereSql.toString());
//                            Expression whereExpression = (Expression) expression;
//                            plain.setWhere(whereExpression);
//                        }
//                    } else {
//                        if (whereSql.length() > 0) {
//                            //where条件之前存在，需要重新进行拼接
//                            whereSql.append(" and ( " + where.toString() + " )");
//                        } else {
//                            //新增片段不存在，使用之前的sql
//                            whereSql.append(where.toString());
//                        }
//                        Expression expression = CCJSqlParserUtil
//                                .parseCondExpression(whereSql.toString());
//                        plain.setWhere(expression);
//                    }
//                    sql2Reset = selectStatement.toString();
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        log.info("schema替换后：{}", sql2Reset);
//        // 替换sql
//        ExecutorPluginUtils.resetSql2Invocation(invocation, sql2Reset);
//
//        return invocation.proceed();
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        return Plugin.wrap(target, this);
//    }
//
//
//    @Override
//    public void setProperties(Properties properties) {
//
//    }
//
//}
//
//
