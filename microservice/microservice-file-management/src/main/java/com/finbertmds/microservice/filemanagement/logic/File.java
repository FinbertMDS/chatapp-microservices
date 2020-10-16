package com.finbertmds.microservice.filemanagement.logic;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(	name = "files" )
public class File {
  
	@Id
  private String fileId;
  
  private String fileName;
  private String fileDownloadUri;
  private String fileType;
  private long size;

  public File(String fileId, String fileName, String fileDownloadUri, String fileType, long size) {
    super();
    this.fileId = fileId;
    this.fileName = fileName;
    this.fileDownloadUri = fileDownloadUri;
    this.fileType = fileType;
    this.size = size;
  }

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileId() {
		return fileId;
	}

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileDownloadUri() {
    return fileDownloadUri;
  }

  public void setFileDownloadUri(String fileDownloadUri) {
    this.fileDownloadUri = fileDownloadUri;
  }

  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
}
