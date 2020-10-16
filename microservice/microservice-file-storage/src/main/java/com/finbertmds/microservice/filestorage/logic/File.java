package com.finbertmds.microservice.filestorage.logic;

import com.finbertmds.microservice.filestorage.utils.UUIDGenerator;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class File {
  private String fileId;
  private String fileName;
  private String fileDownloadUri;
  private String fileType;
  private long size;

  public File(String fileName, String fileDownloadUri, String fileType, long size) {
    super();
    this.fileId = UUIDGenerator.generateType4UUID().toString();
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
