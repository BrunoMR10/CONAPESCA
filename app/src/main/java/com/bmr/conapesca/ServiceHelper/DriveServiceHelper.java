package com.bmr.conapesca.ServiceHelper;

import static com.firebase.ui.auth.AuthUI.TAG;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.os.Environment;
import android.telephony.mbms.DownloadProgressListener;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DriveServiceHelper {
    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    private final Drive mDriveService;
    public DriveServiceHelper(Drive driveService) {
        mDriveService = driveService;
    }
    public Task<String[]> createFolder(String Ticket, String Where) {
        return (Task<String[]>) Tasks.call(mExecutor, () -> {
            String[] Datos;
            String id ;
            if (Where.equals("Preventivo")){
                id= "1isx_CkyYnTgs7Cl8SBLYD6vTxqiW-IVa";
            }else if (Where.equals("Correctivo")){
                id = "1nIgQztmXoFv6mOUQFBEk6Qqfzc-2D3EU";
            }else if (Where.equals("Instalacion")){
                id = "1M1FoieE9ppgeq96KrwbDk0J6XaJTozc5";
            }
            else if (Where.contains("ancel")){
                id = "11sWX-n0-IZn1ZFgqHvjEc--cEpsd1mND";
            }else{
                id= "1lIKo0YgoXj654UdOQN31GWE7r-eJNMwo";
            }
            // File's metadata.
            File fileMetadata = new File();
            //fileMetadata.setName("Test");
            fileMetadata.setParents(Collections.singletonList(id));
            fileMetadata.setName(Ticket);
            fileMetadata.setMimeType("application/vnd.google-apps.folder");



            try {
                File googleFile = mDriveService.files().create(fileMetadata)
                        .setSupportsTeamDrives(true)
                        .setFields("id")
                        .execute();
                System.out.println("Folder ID: " + googleFile.getId());
                System.out.println("Folder LINK: " + googleFile.getWebViewLink());

                Datos= new String[]{
                        googleFile.getId(),
                        googleFile.getWebContentLink()
                };
                return Datos;
            } catch (GoogleJsonResponseException e) {
                // TODO(developer) - handle error appropriately
                System.err.println("Unable to create folder: " + e.getDetails());
                throw e;
            }

        });
    }
    public Task<String> createFolderFotos(String FolderID,String Ticket) {
        return Tasks.call(mExecutor, () -> {
            // File's metadata.
            File fileMetadata = new File();
            //fileMetadata.setName("Test");
            fileMetadata.setParents(Collections.singletonList(FolderID));
            fileMetadata.setName(Ticket+"(CF)");
            fileMetadata.setMimeType("application/vnd.google-apps.folder");



            try {
                File googleFile = mDriveService.files().create(fileMetadata)
                        .setSupportsTeamDrives(true)
                        .setFields("id")
                        .execute();
                System.out.println("Folder ID: " + googleFile.getId());
                return googleFile.getId();
            } catch (GoogleJsonResponseException e) {
                // TODO(developer) - handle error appropriately
                System.err.println("Unable to create folder: " + e.getDetails());
                throw e;
            }

        });
    }
    public Task<Boolean> createBitacora(String FolderID,String SheetID ) {
        return Tasks.call(mExecutor, () -> {
            File file = mDriveService.files().get(SheetID)
                    .setFields("parents")
                    .execute();

            StringBuilder previousParents = new StringBuilder();
            for (String parent : file.getParents()) {
                previousParents.append(parent);
                previousParents.append(',');
            }

            try {
                // Move the file to the new folder
                file = mDriveService.files().update(SheetID, null)
                        .setSupportsTeamDrives(true)
                        .setAddParents(FolderID)
                        .setRemoveParents(previousParents.toString())
                        .setFields("id, parents")
                        .execute();

                return true;
            } catch (GoogleJsonResponseException e) {
                // TODO(developer) - handle error appropriately
                System.err.println("Unable to move file: " + e.getDetails());
                throw e;
            }

        });
    }
    public Task<String> SubirFoto(String FolderID,String ruta,int i ) {
        return Tasks.call(mExecutor, () -> {
            File metadata = null;
            metadata = new File()
                    .setParents(Collections.singletonList(FolderID))
                    .setMimeType("image/png")
                    .setName("Foto"+i);
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            java.io.File directory = cw.getDir(ruta, Context.MODE_PRIVATE);
            java.io.File file = new java.io.File(directory, "Foto"+i+ ".png");
            //String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + java.io.File.separator;
            //String rutacarpeta = "ProyectoX/"+Datos[1]+"/" + Datos[0] + "/" + Datos[0] + "(CF)/";
            //java.io.File filePath = new java.io.File(ExternalStorageDirectory  + rutacarpeta + Name);
            // Specify media type and file-path for file.
            FileContent mediaContent = new FileContent("image/png", new java.io.File(file.getAbsolutePath()));

            File googleFile = mDriveService.files().create(metadata,mediaContent)
                    .setSupportsTeamDrives(true)
                    .execute();

            if (googleFile == null) {
                throw new IOException("Null result when requesting file creation.");
            }

            return googleFile.getId();
        });
    }
    public Task<String> createFile(String FolderID,String Name,String Carpeta) {
        return Tasks.call(mExecutor, () -> {
            File metadata = new File()
                    .setParents(Collections.singletonList(FolderID))
                    .setMimeType("application/pdf")
                    .setName(Name);
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            java.io.File directory = cw.getDir(Carpeta, Context.MODE_PRIVATE);
            java.io.File file = new java.io.File(directory, Name+".pdf");
            // Specify media type and file-path for file.
            FileContent mediaContent = new FileContent("application/pdf", new java.io.File(file.getAbsolutePath()));

            File googleFile = mDriveService.files().create(metadata,mediaContent)
                    .setSupportsTeamDrives(true)
                    .execute();

            if (googleFile == null) {
                throw new IOException("Null result when requesting file creation.");
            }

            return googleFile.getId();
        });
    }
    public Task<Boolean> DescargaImagen(String FileId ) {
        return Tasks.call(mExecutor, () -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                try {
                    OutputStream oOutputStream = new FileOutputStream("sDestinationPath");
                    mDriveService.files().get(FileId).executeMediaAndDownloadTo(oOutputStream);
                    oOutputStream.flush();
                    oOutputStream.close();
                    return true;
                } catch (IOException e) {
                    return false;
                }

            }
            return null;
        });
    }
}
