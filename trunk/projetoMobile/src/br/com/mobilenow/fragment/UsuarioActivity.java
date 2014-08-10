package br.com.mobilenow.fragment;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.util.Date;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.Dialog;
import org.holoeverywhere.app.ProgressDialog;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import br.com.mobilenow.R;
import br.com.mobilenow.ServiceBoxApplication;
import br.com.mobilenow.domain.Usuario;
import br.com.servicebox.common.activity.CommonActivity;
import br.com.servicebox.common.fragment.CommonClosableOnRestoreDialogFragment;
import br.com.servicebox.common.fragment.CommonFragment;
import br.com.servicebox.common.image.ImageFileSystemFetcher;
import br.com.servicebox.common.image.ImageResizer;
import br.com.servicebox.common.util.CommonUtils;
import br.com.servicebox.common.util.FileUtils;
import br.com.servicebox.common.util.GuiUtils;
import br.com.servicebox.common.util.ImageUtils;
import br.com.servicebox.net.Response;

public class UsuarioActivity extends CommonActivity{
	
	static final String TAG = UsuarioActivity.class.getSimpleName();
	private static final int REQUEST_GALLERY = 0;
	private static final int REQUEST_CAMERA = 1;
	protected static final int RESULT_CODE = 123;
	
	
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new UsuarioUiFragment())
                    .commit();
        }		
		
	}
	
	public static class UsuarioUiFragment extends CommonFragment
	implements OnClickListener
	{
		static final String UPLOAD_IMAGE_FILE = "UploadActivityFile";
		static final String UPLOAD_IMAGE_FILE_ORIGINAL = "UploadActivityFileOriginal";
		static final String UPLOAD_IMAGE_FILE_URI = "UploadActivityFileUri";
		static final String SELECTED_ALBUMS = "SELECTED_ALBUMS";
		
		private File mUploadImageFile;
		private File mUploadImageFileOriginal;
		private Uri fileUri;
		
		private EditText edLogin;
		private EditText edNome;
		private EditText edSobrenome;
		private EditText edTelefone;
		private EditText edSenha;
		private EditText edConfSenha;
		private String sexo;
		private RadioGroup rgSexo;
		private Usuario usuario;
		private ProgressDialog progressDialog;
		
		private SelectImageDialogFragment imageSelectionFragment;
		/**
		 * This variable controls whether the dialog should be shown in the
		 * onResume action
		 */
		private boolean showSelectionDialogOnResume = false;
		
		static WeakReference<UsuarioUiFragment> currentInstance;
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    currentInstance = new WeakReference<UsuarioUiFragment>(this);
		    if (savedInstanceState != null)
		    {
		        mUploadImageFile = CommonUtils.getSerializableFromBundleIfNotNull(
		                UPLOAD_IMAGE_FILE, savedInstanceState);
		        mUploadImageFileOriginal = CommonUtils.getSerializableFromBundleIfNotNull(
		                UPLOAD_IMAGE_FILE_ORIGINAL, savedInstanceState);
		        String fileUriString = savedInstanceState.getString(UPLOAD_IMAGE_FILE_URI);
		        if (fileUriString != null)
		        {
		            fileUri = Uri.parse(fileUriString);
		        }
		    }		    
		}
		
		@Override
		public void onDestroy() {
		    super.onDestroy();
		    if (currentInstance != null)
		    {
		        if (currentInstance.get() == UsuarioUiFragment.this
		                || currentInstance.get() == null)
		        {
		            CommonUtils.debug(TAG, "Nullify current instance");
		            currentInstance = null;
		        } else
		        {
		            CommonUtils.debug(TAG,
		                    "Skipped nullify of current instance, such as it is not the same");
		        }
		    }
		}
		
		
		
		@Override
		public View onCreateView(LayoutInflater inflater,
		        ViewGroup container, Bundle savedInstanceState) {
		    super.onCreateView(inflater, container, savedInstanceState);
		    View v = inflater.inflate(R.layout.usuario_fragment, container, false);		    
		    return v;
		}
		
		@Override
		public void onResume() {
		    super.onResume();		    
		    if (showSelectionDialogOnResume)
		    {
		        showSelectionDialog();
		    }
		}
		
		@Override
		public void onViewCreated(View view, Bundle savedInstanceState) {
		    super.onViewCreated(view, savedInstanceState);
		    init(view, savedInstanceState);
		}
		
		@Override
		public void onSaveInstanceState(Bundle outState) {
		    super.onSaveInstanceState(outState);
		    outState.putSerializable(UPLOAD_IMAGE_FILE, mUploadImageFile);
		    outState.putSerializable(UPLOAD_IMAGE_FILE_ORIGINAL, mUploadImageFileOriginal);		   
		    if (fileUri != null)
		    {
		        outState.putString(UPLOAD_IMAGE_FILE_URI, fileUri.toString());
		    }
		}
		
		void init(View v, Bundle savedInstanceState)
		{

		    edLogin = (EditText) getView().findViewById(R.id.login);
			edNome = (EditText) getView().findViewById(R.id.nome);
			edSobrenome = (EditText) getView().findViewById(R.id.sobrenome);
			edTelefone = (EditText) getView().findViewById(R.id.telefone);
			edSenha = (EditText) getView().findViewById(R.id.senha);
			edConfSenha = (EditText) getView().findViewById(R.id.confirma);
			rgSexo = (RadioGroup) getView().findViewById(R.id.sexo);
			if(rgSexo.getCheckedRadioButtonId() == R.id.masculino){
				sexo = "M";
			}else{
				sexo = "F";
			}
			
			Button btRegistrar = (Button) getView().findViewById(R.id.registrar);
			btRegistrar.setOnClickListener(this);		    
		    v.findViewById(R.id.image_upload).setOnClickListener(this);		    
		
		    Intent intent = getActivity().getIntent();
		    boolean showOptions = true;
		    if (intent != null)
		    {
		       
		    }
		    /**
		    if (mUploadImageFile != null)
		    {
		        showOptions = !setSelectedImageFile();
		    }
		    if (showOptions)
		    {
		        showSelectionDialog();
		    }	
		    **/	   
		    
		}		
		
		
		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
		    super.onActivityResult(requestCode, resultCode, data);
		    if (checkNoUploadImageSelectedResult(requestCode, resultCode, data)) {
		        // TrackerUtils.trackUiEvent("uploadNoImageSelectedResult",
		        //        requestCode == REQUEST_GALLERY ? "gallery" : "camera");
		        showSelectionDialogOnResume = true;
		        if (requestCode == REQUEST_CAMERA)
		        {
		            removeGalleryEntryForCurrentFile();
		        }
		        return;
		    }
		    switch (requestCode) {		        
		        
		        case REQUEST_GALLERY:
		            if (resultCode == RESULT_OK && data.getData() != null) {
		                setSelectedImageUri(data.getData());
		            }
		            break;
		        case REQUEST_CAMERA:
		            if (resultCode == RESULT_OK) {
		                updateingGalleryPictureSize();
		                setSelectedImageFile();
		            } else {
		                mUploadImageFile = null;
		            }
		            break;		        
		        default:
		            break;
		    }
		}
		
		private boolean checkNoUploadImageSelectedResult(int requestCode, int resultCode,
		        Intent data) {
		    boolean result = resultCode != RESULT_OK && (requestCode == REQUEST_GALLERY
		            || requestCode == REQUEST_CAMERA);
		    if (!result && resultCode == RESULT_OK && requestCode == REQUEST_GALLERY)
		    {
		        Uri selectedImageUri = data.getData();
		        if (selectedImageUri != null)
		        {
		            String selectedImage = selectedImageUri.toString();
		            if (selectedImage.indexOf("content://com.android.gallery3d.provider") != -1
		                    || selectedImage
		                            .indexOf("content://com.android.sec.gallery3d.provider") != -1
		                    || selectedImage.indexOf("content://com.google.android.gallery3d") != -1)
		            {
		                //TrackerUtils.trackErrorEvent("unsupported_gallery_upload", selectedImage);
		                GuiUtils.alert(R.string.errorUploadPicassaNaoSuportado);
		                result = true;
		            }
		        }
		    }
		    return result;
		}
		
		@Override
		public void onActivityResultUI(int requestCode, int resultCode, Intent data) {
		    super.onActivityResultUI(requestCode, resultCode, data);
		    if (checkNoUploadImageSelectedResult(requestCode, resultCode, data)) {
		        showSelectionDialog();
		        return;
		    }
		    // discard delayed selection dialog showing if planned
		    showSelectionDialogOnResume = false;
		    // this is necessary because onActivityResultDelayed is called
		    // after the onCreateView method so the dialog may appear there if
		    // view were recreated and here need to be closed
		    if (imageSelectionFragment != null && !imageSelectionFragment.isDetached())
		    {
		        imageSelectionFragment.dismissAllowingStateLoss();
		        imageSelectionFragment = null;
		    }
		}
		
		public void removeGalleryEntryForCurrentFile()
		{
		    CommonUtils.debug(TAG, "Removing empty gallery entry: " + fileUri);
		    //TrackerUtils.trackBackgroundEvent("removeGalleryEntryForCurrentFile", TAG);
		    // #271 fix, using another context instead of getActivity()
		    int rowsDeleted = ServiceBoxApplication.getContext().getContentResolver()
		            .delete(fileUri, null, null);
		
		    CommonUtils.debug(TAG, "Rows deleted:" + rowsDeleted);
		}
		
		public void updateingGalleryPictureSize()
		{
		    int sdk = android.os.Build.VERSION.SDK_INT;
		    if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB)
		    {
		        return;
		    }
		 //   TrackerUtils.trackBackgroundEvent("updateingGalleryPictureSize", TAG);
		
		    CommonUtils.debug(TAG, "Updating gallery entry: " + fileUri);
		    BitmapFactory.Options options = ImageResizer.calculateImageSize(mUploadImageFile
		            .getAbsolutePath());
		    ContentValues values = new ContentValues();
		    values.put(MediaStore.Images.Media.WIDTH, options.outWidth);
		    values.put(MediaStore.Images.Media.HEIGHT, options.outHeight);
		    // #271 fix, using another context instead of getActivity()
		    int rowsUpdated = ServiceBoxApplication.getContext().getContentResolver()
		            .update(fileUri, values, null, null);
		    CommonUtils.debug(TAG, "Rows updated:" + rowsUpdated);
		} // fim metodo updateingGalleryPictureSize
		
		public File getNextFileName(String prefix) throws IOException
		{
		    return new File(FileUtils
		            .getStorageFolder(getActivity()),
		            prefix + new Date().getTime() + ".jpg");
		} // fim metodo
		
		public void showSelectionDialog()
		{
		    if (imageSelectionFragment != null)
		    {
		        //TrackerUtils.trackUiEvent("imageSelectionDialogCreation.skipped", TAG);
		        return;
		    }
		    // if instance is saved we can't show dialog such as it will cause
		    // illegal state exception. Instead we plan showing in the onResume
		    // event if it will appear
		    /**
		    if (isInstanceSaved())
		    {
		        //TrackerUtils.trackUiEvent("imageSelectionDialogCreation.delayedToOnResume", TAG);
		        showSelectionDialogOnResume = true;
		        return;
		    }
		    **/
		    //TrackerUtils.trackUiEvent("imageSelectionDialogCreation.show", TAG);
		    showSelectionDialogOnResume = false;
		    imageSelectionFragment = SelectImageDialogFragment
		            .newInstance(new SelectImageDialogFragment.SelectedActionHandler() {
		
		                private static final long serialVersionUID = 1L;
		
		                @Override
		                public void cameraOptionSelected() {
		                    try {
		                        mUploadImageFile = getNextFileName("upload_");
		                        // this is a hack for some
		                        // devices taken from here
		                        // http://thanksmister.com/2012/03/16/android_null_data_camera_intent/
		                        ContentValues values = new ContentValues();
		                        values.put(MediaStore.Images.Media.TITLE,
		                                mUploadImageFile.getName());
		                        values.put(MediaStore.Images.Media.DATA,
		                                mUploadImageFile.getAbsolutePath());
		
		                        Intent intent = new Intent(
		                                MediaStore.ACTION_IMAGE_CAPTURE);
		
		                        fileUri = getActivity()
		                                .getContentResolver()
		                                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
		                                        values);
		                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		
		                        startActivityForResult(intent, REQUEST_CAMERA);
		
		                    } catch (IOException e) {
		                        GuiUtils.error(
		                                TAG,
		                                R.string.errorCanNotFindExternalStorageForTakingPicture,
		                                e);
		                    }
		                }
		
		                @Override
		                public void galleryOptionSelected() {
		                    Intent intent = new Intent(Intent.ACTION_PICK);
		                    intent.setType("image/*");
		                    startActivityForResult(intent, REQUEST_GALLERY);
		                }
		
		                @Override
		                public void onDismiss() {
		                    imageSelectionFragment = null;
		                }
		            });
		    imageSelectionFragment.show(getSupportActivity());
		} // fim metodo showSelectionDialog
		
		private void setSelectedImageUri(Uri imageUri) {
		    mUploadImageFile = new File(ImageUtils.getRealPathFromURI(getActivity(), imageUri));
		    setSelectedImageFile();
		}
		
		private boolean setSelectedImageFile() {
		    if (!mUploadImageFile.exists())
		    {
		        mUploadImageFile = null;
		        mUploadImageFileOriginal = null;
		        return false;
		    }
		    ImageView previewImage = (ImageView) getView().findViewById(R.id.image_upload);
		    previewImage.setImageBitmap(ImageFileSystemFetcher.processBitmap(
		            mUploadImageFile.getAbsolutePath(), 200, 200));
		    return true;
		}
		
		@Override
		public void onClick(View v) {
		    switch (v.getId()) {		        
		        
		        case R.id.registrar:
		            //TrackerUtils.trackButtonClickEvent("button_upload", getActivity());
			        	if(edLogin.getText() == null || edLogin.getText().toString().equals("")){
							edLogin.setError("Login inválido.");
							return;
						}
						if(edNome.getText() == null || edNome.getText().toString().equals("")){
							edNome.setError("Nome inválido");
							return;
						}
						
						if(edSobrenome.getText() == null || edSobrenome.getText().toString().equals("")){
							edSobrenome.setError("Sobrenome inválido.");
						}
						
						if(edTelefone.getText() == null || edTelefone.getText().toString().equals("")){
							edTelefone.setError("Telefone inválido.");
							return;
						}
						if(edSenha.getText() == null || edSenha.getText().toString().equals("")){
							edSenha.setError("Senha inválida.");
							return;
						}
						if(edSenha.getTextSize() < 7){
							edSenha.setError("Senha deve ter no minimo 7 caracteres.");
							return;
						}
						
						if(edConfSenha.getText() == null || edConfSenha.getText().toString().equals("") ||
								      !edSenha.getText().toString().equals(edConfSenha.getText().toString())){
							edConfSenha.setError("Confime sua senha");
							return;
						}
						
						usuario = new Usuario(edLogin.getText().toString(), edSenha.getText().toString(), 
								           edNome.getText().toString(), edSobrenome.getText().toString(), sexo, null);
										
				 
		            if (mUploadImageFile != null) {
		            	new RequisicaoTask().execute();		                
		            } else
		            {
		                GuiUtils.alert(R.string.escolha_foto_primeiro);
		                showSelectionDialog();
		            }
		            break;		        
		        case R.id.image_upload:
		            //TrackerUtils.trackButtonClickEvent("image_upload", getActivity());
		            if (mUploadImageFile != null)
		            {
		                Intent intent = new Intent();
		                intent.setAction(android.content.Intent.ACTION_VIEW);
		                intent.setDataAndType(Uri.fromFile(mUploadImageFile), "image/png");
		                startActivity(intent);
		            } else
		            {
		                showSelectionDialog();
		            }
		            break;
		    }
		}		
		
				
		@Override
		public void onDestroyView() {
		    super.onDestroyView();		   
		}		
		
		public static class SelectImageDialogFragment extends CommonClosableOnRestoreDialogFragment
		{
		    public static interface SelectedActionHandler extends Serializable
		    {
		        void cameraOptionSelected();
		
		        void galleryOptionSelected();
		
		        void onDismiss();
		    }
		
		    private SelectedActionHandler handler;
		
		    public static SelectImageDialogFragment newInstance(
		            SelectedActionHandler handler)
		    {
		        SelectImageDialogFragment frag = new SelectImageDialogFragment();
		        frag.handler = handler;
		        return frag;
		    }
		
		    @Override
		    public void onCancel(DialogInterface dialog) {
		        super.onCancel(dialog);
		        getActivity().finish();
		    }
		
		    @Override
		    public void onDismiss(DialogInterface dialog) {
		        super.onDismiss(dialog);
		        if (handler != null)
		        {
		            handler.onDismiss();
		        }
		    }
		
		    @Override
		    public Dialog onCreateDialog(Bundle savedInstanceState) {
		        final CharSequence[] items = {
		                getString(R.string.upload_opcao_camera),
		                getString(R.string.upload_opcao_galeria)
		        };
		
		        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),
		                R.style.Theme_ServiceBox_Dialog_Light);
		        builder.setTitle(R.string.titulo_dialog);
		        builder.setItems(items, new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int item) {
		                if (handler == null)
		                {
		                    return;
		                }
		                switch (item) {
		                    case 0:
		                        //TrackerUtils.trackContextMenuClickEvent("menu_camera",
		                        //        SelectImageDialogFragment.this);
		                        handler.cameraOptionSelected();
		                        return;
		                    case 1:
		                       // TrackerUtils.trackContextMenuClickEvent("menu_gallery",
		                       //         SelectImageDialogFragment.this);
		                        handler.galleryOptionSelected();
		                        return;
		                }
		            }
		        });
		        return builder.create();
		    }
		}
		
		
		private class RequisicaoTask extends AsyncTask<Void, Void, Response>{
			
			@Override
	        protected void onPreExecute() {
				super.onPreExecute();
				progressDialog = new ProgressDialog(getActivity());
				progressDialog.setTitle("Aguarde");
				progressDialog.setMessage("Registrando...");
				progressDialog.setCancelable(false);
				progressDialog.show();
			}

			@Override
			protected Response doInBackground(Void... params) {
				
				  FormHttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
	              formHttpMessageConverter.setCharset(Charset.forName("UTF8"));

				
				try {
					final String url = "http://192.168.0.133:8080/projetoWeb/registrarUsuario.json";
					
					RestTemplate restTemplate = new RestTemplate();
					restTemplate.getMessageConverters().add( formHttpMessageConverter );
					restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
					
					 restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
					 MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
                     
					 map.add("login", usuario.getLogin());
					 map.add("nome", usuario.getNome());
					 map.add("sobrenome", usuario.getSobreNome());
					 map.add("senha", usuario.getPassword());
					 map.add("sexo", usuario.getSexo());
					 map.add("imagemPerfil", mUploadImageFile.getName());
					 
		             map.add("file", new FileSystemResource(mUploadImageFile));
		             
		             HttpHeaders imageHeaders = new HttpHeaders();
		             imageHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);

		             HttpEntity<MultiValueMap<String, Object>> imageEntity = new HttpEntity<MultiValueMap<String, Object>>(map, imageHeaders);

	                //restTemplate.exchange(url, HttpMethod.POST, imageEntity, Usuario.class);
					Response response = restTemplate.postForObject(url, imageEntity, Response.class);
					
					return 	response;
				}catch(ResourceAccessException rae){
					CommonUtils.error(TAG, rae.getMessage());
					return new Response(false, "Falha no cadastro do usuário \n Servidor não responde.", null);
				} catch (Exception e) {
					Log.e("UsuarioActivity", e.getMessage());
					return new Response(false, "Fallha no cadastro do usuário, tente novamente mais tarde.", null);
				}
				
				
			}
			
			@Override
			protected void onCancelled() {
				super.onCancelled();
			}
			
			@Override
			protected void onPostExecute(Response result) {				
				super.onPostExecute(result);
				progressDialog.dismiss();
			}
			
		}

		
		
	}
			
			
			
			
			

}
