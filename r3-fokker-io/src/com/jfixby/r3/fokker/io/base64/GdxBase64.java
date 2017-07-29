
package com.jfixby.r3.fokker.io.base64;

import java.io.IOException;

import com.badlogic.gdx.utils.Base64Coder;
import com.jfixby.scarabei.api.base64.Base64Component;
import com.jfixby.scarabei.api.io.InputStream;
import com.jfixby.scarabei.api.java.ByteArray;
import com.jfixby.scarabei.api.strings.Strings;
import com.jfixby.scarabei.api.util.Utils;

public class GdxBase64 implements Base64Component {

	@Override
	public ByteArray decode (final String dataInBase64) throws IOException {
		return Utils.newByteArray(Base64Coder.decode(dataInBase64));
	}

	@Override
	public String encode (final InputStream is) throws IOException {
		return this.encode(is.readAll());
	}

	@Override
	public String encode (final ByteArray bytes) throws IOException {
		return Strings.newString(Base64Coder.encode(bytes.toArray()));
	}

}
