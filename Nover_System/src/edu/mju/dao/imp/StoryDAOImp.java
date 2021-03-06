package edu.mju.dao.imp;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import edu.mju.dao.CategoryDAO;
import edu.mju.dao.StoryDAO;
import edu.mju.pojo.CategoryBean;
import edu.mju.pojo.ChapterBean;
import edu.mju.pojo.StoryBean;
import edu.mju.util.FindPropertiesUtil;
import edu.mju.util.JdbcUtil;

@Repository(value = "storyDAOImp")
public class StoryDAOImp extends JdbcDaoSupport implements StoryDAO {

	private DataSource dataSource;

	@Autowired
	public void setDataSourceX(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	/**
	 * 插入操作
	 */
	public void insert(Object[] objectArray) {
		StringBuffer insertSQL = new StringBuffer();
		insertSQL.append("Insert Into ns_nover_chapter");
		insertSQL.append("(chapter_id , story_id,chapter_name,");
		insertSQL.append("word_count,publish_time)");
		insertSQL.append("Values (?,?,?,?,?)");

		int rowConut = this.getJdbcTemplate().update(insertSQL.toString(),
				objectArray);
		System.out.println("影响的行数 ：" + rowConut);
	}

	/**
	 * 查找根据ID
	 * 
	 * @param objectArray
	 * @return
	 */
	@Override
	public StoryBean findById(String chapter_id) {
		StringBuffer sql = new StringBuffer();
		// sql.append( "Select * From ns_nover_story Where storyr_id = '"
		// + chapter_id + "'");
		sql.append("Select * From ns_nover_story ");
		sql.append("Where story_id in(");
		sql.append("Select story_id From ns_nover_chapter ");
		sql.append("Where chapter_id = '" + chapter_id + "')");
		JdbcTemplate template = this.getJdbcTemplate();

		BeanPropertyRowMapper<StoryBean> rowMapper = new BeanPropertyRowMapper<StoryBean>(
				StoryBean.class);
		StoryBean storyBean = template
				.queryForObject(sql.toString(), rowMapper);

		System.out.println("chapterBean.getChapter_id()"
				+ storyBean.getStory_id() + "---------------------------");
		return storyBean;
	}

	/**
	 * 查找所有信息操作
	 * 
	 * @param objectArray
	 * @return
	 */
	@Override
	public List<StoryBean> findList(String category_id) {
		QueryRunner runner = new QueryRunner();
		String sql = null;
		if (category_id.equals("") || category_id == null) {
			sql = "Select * From ns_nover_story order by deptid asc";
		} else {
			sql = "Select * From ns_nover_story Where story_id in ( Select"
					+ " story_id From ns_nover_category_story Where category_id ='"
					+ category_id + "')";
		}
		BeanListHandler<StoryBean> handler = new BeanListHandler<StoryBean>(
				StoryBean.class);
		Connection conn = null;
		List<StoryBean> storyList = null;
		try {
			conn = JdbcUtil.getConn();
			storyList = runner.query(conn, sql, handler);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(conn);
		}
		return storyList;
	}

	/**
	 * 修改操作
	 * 
	 * @param objectArray
	 * @return
	 */
	public void modify(CategoryBean categoryBean) {
		StringBuffer modify = new StringBuffer();
		modify.append("Update ns_nover_category Set");
		modify.append("category_name = :category_name,");
		modify.append("describe = :describe");
		modify.append("Where category_id = :category_id");
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(
				this.dataSource);
		BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(
				categoryBean);
		template.update(modify.toString(), parameterSource);
	}

	/**
	 * 删除操作
	 * 
	 * @param objectArray
	 */
	public void delete(String chapter_id) {
		String delete = "Delete From ns_nover_chapter Where chapter_id = '"
				+ chapter_id + "'";
		int row = this.getJdbcTemplate().update(delete);
	}

}
