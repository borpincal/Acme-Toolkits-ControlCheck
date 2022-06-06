package acme.entities.goti;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.entities.inventions.Invention;
import acme.entities.systemConfiguration.SystemConfiguration;
import acme.framework.datatypes.Money;
import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import spam.detector.SpamDetector;

@Entity
@Getter
@Setter
public class Goti extends AbstractEntity {

	// Serialisation identifier ----------------------------------------
	
	protected static final long serialVersionUID		= 1L;
	
	// Atributes -------------------------------------------------------
	
	@NotBlank
	@Pattern(regexp = "^[0-9]{6}:[0-9]{2}:[0-9]{2}:[0-9]{2}$")
	protected String			code;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				creationTime;
	
	@NotNull
	@NotBlank
	@Length(min = 0, max = 100)
	private String 				theme;
	
	@NotBlank
	@Length(min = 0, max = 255)
	protected String			summary;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				startTime;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				endTime;
	
	@NotNull
	@Valid
	protected Money				quantity;
	
	@URL
	protected String			furtherInfo;
	
	// Relationships-------------
	@Valid
	@OneToOne
	protected Invention				invention;
	
	public boolean isSpam(final SystemConfiguration systemConfiguration) {
		
		final String text = this.getSummary() + "\n" + this.getTheme();
		return SpamDetector.isSpam(text, systemConfiguration.getWeakSpamTerms(), systemConfiguration.getStrongSpamTerms(), systemConfiguration.getStrongSpamTermsThreshold(), systemConfiguration.getWeakSpamTermsThreshold());

	}
	
	
}
