import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FileService {
  private readonly STORAGE_KEY = 'my-app-files';
  private readonly UPLOADS_DIR = '/uploads/';

  constructor() { }

  saveFile(file: File): string {
    const storedFiles = this.getStoredFiles();
    storedFiles.push(file);
    this.updateStoredFiles(storedFiles);

    // save file to local storage
    const blob = new Blob([file], { type: file.type });
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = file.name;
    link.click();
    window.URL.revokeObjectURL(url);
    return url;
  }


  private getStoredFiles(): File[] {
    const storedFilesString = localStorage.getItem(this.STORAGE_KEY);
    if (storedFilesString) {
      return JSON.parse(storedFilesString);
    } else {
      return [];
    }
  }

  private updateStoredFiles(files: File[]): void {
    localStorage.setItem(this.STORAGE_KEY, JSON.stringify(files));
  }

  getStoredFileUrl(file: File): string {
    return URL.createObjectURL(file);
  }

  getUploadsDir(): string {
    return this.UPLOADS_DIR;
  }
}
