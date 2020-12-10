package org.doif.projectv.common.resource.repository.menu;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.MenuDto;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MenuRepositoryImpl implements MenuQueryRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<MenuDto.Result> findAllRecursiveCategoryAndMenu() {
        String query = "with recursive cte (resource_id, parent_id, name, description, padding_name, sort, depth, path, type, status, icon, url) as (\n" +
                "    select A.RESOURCE_ID\n" +
                "         , A.PARENT_ID\n" +
                "         , B.NAME\n" +
                "         , B.DESCRIPTION\n" +
                "         , LPAD(B.NAME, length(b.NAME), ' ') AS PADDING_NAME\n" +
                "         , A.SORT\n" +
                "         , 1 AS DEPTH\n" +
                "         , convert(A.SORT, varchar(5)) as path\n" +
                "         , 'CATEGORY' AS TYPE\n" +
                "         , B.STATUS\n" +
                "         , A.ICON\n" +
                "         , '' AS URL\n" +
                "    from resource_menu_category A\n" +
                "             inner join resource B ON A.RESOURCE_ID = B.RESOURCE_ID\n" +
                "    WHERE A.PARENT_ID IS NULL\n" +
                "    UNION ALL\n" +
                "    SELECT *\n" +
                "    FROM (\n" +
                "             SELECT A.RESOURCE_ID\n" +
                "                  , A.PARENT_ID\n" +
                "                  , B.NAME\n" +
                "                  , B.DESCRIPTION\n" +
                "                  , LPAD(B.NAME, length(b.NAME) + D.depth + 1, ' ')\n" +
                "                  , A.SORT\n" +
                "                  , D.depth + 1\n" +
                "                  , concat(D.path, '-', A.SORT)\n" +
                "                  , 'CATEGORY' AS TYPE\n" +
                "                  , B.STATUS\n" +
                "                  , A.ICON\n" +
                "                  , '' AS URL\n" +
                "             from resource_menu_category A\n" +
                "                      inner join resource B ON A.RESOURCE_ID = B.RESOURCE_ID\n" +
                "                      inner join cte D on D.RESOURCE_ID = A.PARENT_ID\n" +
                "             UNION ALL\n" +
                "             SELECT A.RESOURCE_ID\n" +
                "                  , A.MENU_CATEGORY_ID\n" +
                "                  , B.NAME\n" +
                "                  , B.DESCRIPTION\n" +
                "                  , LPAD(B.NAME, length(b.NAME) + D.depth + 1, ' ')\n" +
                "                  , A.SORT\n" +
                "                  , D.depth + 1\n" +
                "                  , concat(D.path, '-', A.SORT)\n" +
                "                  , 'MENU' AS TYPE\n" +
                "                  , B.STATUS\n" +
                "                  , A.ICON\n" +
                "                  , A.URL\n" +
                "             from resource_menu A\n" +
                "                      inner join resource B ON A.RESOURCE_ID = B.RESOURCE_ID\n" +
                "                      inner join cte D on D.RESOURCE_ID = A.MENU_CATEGORY_ID\n" +
                "             )\n" +
                ")\n" +
                "select resource_id AS resourceId,\n" +
                "       parent_id AS parentId,\n" +
                "       name,\n" +
                "       description,\n" +
                "       padding_name AS paddingName,\n" +
                "       sort,\n" +
                "       depth,\n" +
                "       path,\n" +
                "       type,\n" +
                "       status,\n" +
                "       icon,\n" +
                "       url\n" +
                "from cte\n" +
                "order by path";

        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(MenuDto.Result.class));
    }
}
