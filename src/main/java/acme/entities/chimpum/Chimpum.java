package acme.entities.chimpum;

import java.util.Date;

import javax.persistence.Column;
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
public class Chimpum extends AbstractEntity {

	// Serialisation identifier ----------------------------------------
	
	protected static final long serialVersionUID		= 1L;
	
	// Atributes -------------------------------------------------------
	
	@NotBlank
	@Pattern(regexp = "^[A-Z]{3}-[0-9]{3}(-[A-Z])-[0-9]{4}-[0-9]{2}-[0-9]{2}?$")
	@Column(unique=true)
	protected String			code;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	protected Date				creationTime;
	
	@NotNull
	@NotBlank
	@Length(min = 0, max = 100)
	private String 				title;
	
	@NotBlank
	@Length(min = 0, max = 255)
	protected String			description;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				startTime;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				endTime;
	
	@NotNull
	@Valid
	protected Money				budget;
	
	@URL
	protected String			link;
	
	// Relationships-------------
	@Valid
	@OneToOne
	protected Invention				invention;
	
	public boolean isSpam(final SystemConfiguration systemConfiguration) {
		
		final String text = this.getDescription() + "\n" + this.getTitle();
		return SpamDetector.isSpam(text, systemConfiguration.getWeakSpamTerms(), systemConfiguration.getStrongSpamTerms(), systemConfiguration.getStrongSpamTermsThreshold(), systemConfiguration.getWeakSpamTermsThreshold());

	}
	
	
}
