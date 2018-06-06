package com.bcits.bfm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@NamedQueries({ 
    @NamedQuery(name = "HelpDeskSettingsEntity.getAllRoleIds", query = "SELECT ps.rlId FROM HelpDeskSettingsEntity ps ORDER BY ps.settingId DESC"),
	@NamedQuery(name = "HelpDeskSettingsEntity.findAll", query = "SELECT hse.settingId,hse.rlId FROM HelpDeskSettingsEntity hse ORDER BY hse.settingId DESC"),
	@NamedQuery(name = "HelpDeskSettingsEntity.getAllActiveRoles", query = "SELECT r.rlId,r.rlName from Role r WHERE r.rlStatus = 'Active' AND r.rlId NOT IN (SELECT das.rlId FROM HelpDeskSettingsEntity das)")
})

@Entity
@Table(name = "HELP_DESK_SETTINGS")
public class HelpDeskSettingsEntity {

	@Id
	@SequenceGenerator(name = "helpDeskSettings", sequenceName = "HELPDESK_SETTINGS_SEQ")
	@GeneratedValue(generator = "helpDeskSettings")
	@Column(name = "HD_SETTING_ID")
	private int settingId;
	
	@Min(value = 1, message = "Role Is Not Selected")
	@Column(name = "RL_ID")
	private int rlId;

	public int getSettingId() {
		return settingId;
	}

	public void setSettingId(int settingId) {
		this.settingId = settingId;
	}

	public int getRlId() {
		return rlId;
	}

	public void setRlId(int rlId) {
		this.rlId = rlId;
	}
	
}
