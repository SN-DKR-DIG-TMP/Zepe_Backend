// BY MOMATH NDIAYE

package com.zepebackend.batch.config;

import com.zepebackend.batch.model.FactureModel;
import com.zepebackend.batch.services.FactureModelItemProcessor;
import com.zepebackend.utils.ZepeConstants;
import com.zepebackend.utils.ZepeUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.zepebackend.utils.ZepeConstants.DATA_LOAD;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

	public final JobBuilderFactory jobBuilderFactory;
	public final StepBuilderFactory stepBuilderFactory;
	public final DataSource dataSource;
	private final ItemWriter<FactureModel> factureModelItemWriter;

	public SpringBatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
			DataSource dataSource, ItemWriter<FactureModel> factureModelItemWriter) {
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
		this.dataSource = dataSource;
		this.factureModelItemWriter = factureModelItemWriter;
	}

	@Bean
	public JdbcCursorItemReader<FactureModel> reader() {

		JdbcCursorItemReader<FactureModel> reader = new JdbcCursorItemReader<FactureModel>();
		reader.setDataSource(dataSource);

		reader.setSql("select sum(a.amount) total , a.trade , a.business, p.id_partenariat \n"
				+ "from cashment a, partenariat p \n" + "where a.business = p.id_entreprise\n"
				+ "and a.trade = p.id_commerce\n" + "and a.is_valid = true\n" +"and a.date between '" + ZepeUtils.debutFinDuMois().get(ZepeConstants.START)
				+ "'  and   '" + ZepeUtils.debutFinDuMois().get(ZepeConstants.END) + "' "
				+ "group by a.trade,a.business, p.id_partenariat");

		reader.setRowMapper(new UserRowMapper());
		return reader;
	}

	public FactureModelItemProcessor processor() {
		return new FactureModelItemProcessor();
	}

	@Bean
	public Job factureJob() {
		Step step1 = stepBuilderFactory.get(DATA_LOAD).<FactureModel, FactureModel>chunk(10).reader(reader())
				.processor(processor()).writer(factureModelItemWriter).build();

		return jobBuilderFactory.get(DATA_LOAD).incrementer(new RunIdIncrementer()).start(step1).build();
	}

	public class UserRowMapper implements RowMapper<FactureModel> {
		@Override
		public FactureModel mapRow(ResultSet resultSet, int i) throws SQLException {
			FactureModel factureModel = new FactureModel();
			factureModel.setBusinessId(resultSet.getLong("business"));
			factureModel.setMontant(resultSet.getDouble("total"));
			factureModel.setTradeId(resultSet.getLong("trade"));
			factureModel.setDateFacture(ZepeUtils.debutFinDuMois().get(ZepeConstants.END).toDate());
			factureModel.setPeriode("Du " + ZepeUtils.convertDateTieToString(ZepeUtils.debutFinDuMois().get(ZepeConstants.START))
					+ " au " + ZepeUtils.convertDateTieToString(ZepeUtils.debutFinDuMois().get(ZepeConstants.END)));
			factureModel.setPartenariatId(resultSet.getLong("id_partenariat"));
			return factureModel;
		}
	}
}
