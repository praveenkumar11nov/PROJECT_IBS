package com.bcits.bfm.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NamedQueries({ 
	@NamedQuery(name = "CamReportSettingsEntity.findAll", query = "SELECT cr.reportSettingId,cr.particularShown FROM CamReportSettingsEntity cr ORDER BY cr.reportSettingId DESC"),
	@NamedQuery(name = "CamReportSettingsEntity.findAllData", query = "SELECT cr FROM CamReportSettingsEntity cr"),
})


@Entity
@Table(name = "CAM_REPORT_SETTINGS")
public class CamReportSettingsEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		@Id
		@SequenceGenerator(name = "cam_report_settings_seq", sequenceName = "CAM_REPORT_SETTINGS_SEQ")
		@GeneratedValue(generator = "cam_report_settings_seq")
		@Column(name = "ID")
		private int reportSettingId;
		
		@Column(name="PARTICULARS_SHOWN")
		private String particularShown;

		public int getReportSettingId() {
			return reportSettingId;
		}

		public void setReportSettingId(int reportSettingId) {
			this.reportSettingId = reportSettingId;
		}

		public String getParticularShown() {
			return particularShown;
		}

		public void setParticularShown(String particularShown) {
			this.particularShown = particularShown;
		}

}
		
		